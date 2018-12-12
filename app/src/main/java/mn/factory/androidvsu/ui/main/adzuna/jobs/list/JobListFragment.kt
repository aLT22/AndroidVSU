package mn.factory.androidvsu.ui.main.adzuna.jobs.list


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_settings_job.*
import kotlinx.android.synthetic.main.fragment_job_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import mn.factory.androidvsu.BR
import mn.factory.androidvsu.R
import mn.factory.androidvsu.databinding.FragmentJobListBinding
import mn.factory.androidvsu.model.ItemPresentation
import mn.factory.androidvsu.model.adzuna.job.JobPresentation
import mn.factory.androidvsu.ui.adapter.rv.adzuna.jobs.JobsRecyclerAdapter
import mn.factory.androidvsu.ui.main.MainActivity
import mn.factory.androidvsu.utils.listeners.EndlessScrollListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class JobListFragment : Fragment() {

    private val mViewModel: JobListVM by viewModel()
    private val mSettingsViewModel: JobListSettingsVM by viewModel()

    private lateinit var mBinding: FragmentJobListBinding
    private val mJobsAdapter: JobsRecyclerAdapter by inject()

    private var mEndlessScrollListener: EndlessScrollListener? = null
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        mEndlessScrollListener = object : EndlessScrollListener() {
            override fun onLoadMore(page: Int, totalItemsCount: Int, recyclerView: RecyclerView) {
                mViewModel.jobsInteractorRequest.page = page
                mViewModel.fetchJobs(false)
            }
        }
        mViewModel.fetchJobs(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_list, container, false)
        mBinding.setLifecycleOwner(this)
        mBinding.setVariable(BR.vm, mViewModel)

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /**
         * JobsVM block starts
         * */
        mViewModel.getJobs().observe(this, Observer {
            mJobsAdapter.setJobs(it ?: emptyList<JobPresentation>())
        })

        mViewModel.loading.observe(this, Observer {
            mBinding.swipeRefreshLayout.isRefreshing = it!!
        })
        /**
         * JobsVM block ends
         * */

        /**
         * JobListSettingsVM block starts
         * */
        mSettingsViewModel.mSalaryTitle.observe(this, Observer {
            titleSalary.text = it
        })
        /**
         * JobListSettingsVM block ends
         * */
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jobList?.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapter = mJobsAdapter
            setHasFixedSize(true)
            layoutManager?.let {
                mEndlessScrollListener?.mLayoutManager = it
            }
            addOnScrollListener(mEndlessScrollListener as EndlessScrollListener)
        }

        GlobalScope.launch(Dispatchers.Main) {
            salaryOptions?.apply {
                val spinnerAdapter = async(Dispatchers.IO) {
                    ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayOf(PER_YEAR, PER_MONTH))
                }.await()
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                adapter = spinnerAdapter

                mSettingsViewModel.mSalaryOption.value?.apply {
                    setSelection(this)
                }

                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        mSettingsViewModel.mSalaryOption.postValue(position)
                    }
                }
            }
        }

        swipeRefreshLayout?.apply {
            setOnRefreshListener {
                mEndlessScrollListener?.let {
                    it.reset()
                    mViewModel.resetRequest()
                    mViewModel.fetchJobs(true)
                }
            }
        }

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        mBottomSheetBehavior?.apply {
            setBottomSheetTitle()

            setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(p0: View, p1: Float) {
                }

                @SuppressLint("SwitchIntDef")
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    setBottomSheetTitle(newState)
                }
            })

            if (savedInstanceState != null) {
                val savedState = savedInstanceState[KEY_BOTTOM_SHEET_STATE] as Int?
                if (savedState != null) {
                    mBottomSheetBehavior.state = savedState
                }
            }
        }
        title?.apply {
            setOnClickListener {
                if (mBottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                    mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                } else {
                    mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
        }

        salary?.apply {
            clearThumbs()
            addThumb(mSettingsViewModel.mSalaryMin.value?.toInt()!!)
            addThumb(mSettingsViewModel.mSalaryMax.value?.toInt()!!)

            setOnThumbValueChangeListener { _, _, thumbIndex, value ->
                when (thumbIndex) {
                    SLIDER_LEFT_THUMB -> {
                        mSettingsViewModel.mSalaryMin.postValue(value.toString())
                    }
                    SLIDER_RIGHT_THUMB -> {
                        mSettingsViewModel.mSalaryMax.postValue(value.toString())
                    }
                }
            }
        }

        mJobsAdapter.clickObservable.subscribe(
                {
                    (activity as MainActivity).showFragment(ACTION_TO_DETAILS, sharedBundle(it))
                },
                {
                    Log.e(TAG, it.localizedMessage)
                }
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        /**
         * Save bottom sheet state
         * */
        outState.putInt(KEY_BOTTOM_SHEET_STATE, mBottomSheetBehavior.state)
    }

    private fun sharedBundle(jobPresentation: ItemPresentation): Bundle {
        val job = jobPresentation as JobPresentation

        val arguments = Bundle()
        arguments.putParcelable(KEY_JOB, job)

        return arguments
    }

    private fun setBottomSheetTitle() {
        GlobalScope.launch(Dispatchers.Main) {
            when (mBottomSheetBehavior.state) {
                BottomSheetBehavior.STATE_EXPANDED -> {
                    dragOrClick.text = async(Dispatchers.IO) {
                        String.format(Locale.getDefault(), resources.getString(R.string.drag_or_click), CLOSE)
                    }.await()
                }
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    dragOrClick.text = async(Dispatchers.IO) {
                        String.format(Locale.getDefault(), resources.getString(R.string.drag_or_click), OPEN)
                    }.await()
                }
                else -> {
                }
            }
        }
    }

    private fun setBottomSheetTitle(newState: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            when (newState) {
                BottomSheetBehavior.STATE_EXPANDED -> {
                    dragOrClick.text = async(Dispatchers.IO) {
                        String.format(Locale.getDefault(), resources.getString(R.string.drag_or_click), CLOSE)
                    }.await()
                }
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    dragOrClick.text = async(Dispatchers.IO) {
                        String.format(Locale.getDefault(), resources.getString(R.string.drag_or_click), OPEN)
                    }.await()
                }
                else -> {
                }
            }
        }
    }

    companion object {
        const val TAG = "JobListFragment"

        /**
         * SavedInstanceState keys
         * */
        const val KEY_BOTTOM_SHEET_STATE = "BOTTOM_SHEET_STATE"
        /**
         * SavedInstanceState keys
         * */

        /**
         * Navigation actions
         * */
        const val ACTION_TO_DETAILS = R.id.openJobDetailsAction

        /**
         * Settings panel
         * */
        const val SLIDER_LEFT_THUMB = 0
        const val SLIDER_RIGHT_THUMB = 1

        const val OPEN = "open"
        const val CLOSE = "close"

        const val PER_YEAR = "per year"
        const val PER_MONTH = "per month"

        /**
         * Bundle keys
         * */
        private const val KEY_JOB = "JOB"

        fun newInstance() = JobListFragment().apply { retainInstance = true }
    }

}
