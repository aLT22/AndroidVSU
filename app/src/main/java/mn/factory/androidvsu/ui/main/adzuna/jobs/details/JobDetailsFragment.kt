package mn.factory.androidvsu.ui.main.adzuna.jobs.details


import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_job_details.*
import kotlinx.coroutines.*
import mn.factory.androidvsu.BR
import mn.factory.androidvsu.R
import mn.factory.androidvsu.databinding.FragmentJobDetailsBinding
import mn.factory.androidvsu.model.adzuna.job.JobPresentation
import mn.factory.androidvsu.utils.exts.newBrowserIntent
import mn.factory.domain.utils.CoroutinesDispatchers
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.coroutines.CoroutineContext


class JobDetailsFragment : Fragment(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private val mDispatchers: CoroutinesDispatchers by inject()

    private val mViewModel: JobDetailsVM by viewModel()
    lateinit var mBinding: FragmentJobDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        launch(mDispatchers.io()) {
            arguments?.let {
                mViewModel.mJobPresentation = it[KEY_JOB] as JobPresentation

                mViewModel.mTitle.postValue(mViewModel.mJobPresentation?.title)
                if (mViewModel.mJobPresentation?.title != null) mViewModel.mTitleVisibility.postValue(true)
                else mViewModel.mTitleVisibility.postValue(false)

                mViewModel.mDescription.postValue(mViewModel.mJobPresentation?.description)
                if (mViewModel.mJobPresentation?.description != null) mViewModel.mDescriptionVisibility.postValue(true)
                else mViewModel.mDescriptionVisibility.postValue(false)

                mViewModel.mCompany.postValue(mViewModel.mJobPresentation?.company?.displayName)
                if (mViewModel.mJobPresentation?.company?.displayName != null) mViewModel.mCompanyVisibility.postValue(true)
                else mViewModel.mCompanyVisibility.postValue(false)

                if (mViewModel.mJobPresentation?.location?.displayName != null) mViewModel.mLocationVisibility.postValue(true)
                else mViewModel.mLocationVisibility.postValue(false)

                mViewModel.mInfo.postValue(resources.getString(R.string.more_info))
                mViewModel.mInfoVisibility.postValue(true)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_details, container, false)
        mBinding.setLifecycleOwner(this)
        mBinding.setVariable(BR.vm, mViewModel)

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mViewModel.mSalaryOption.observe(this, Observer {
            when (it) {
                JobDetailsVM.PER_MONTH -> {
                    showSalaryPerMonth()
                }
                JobDetailsVM.PER_YEAR -> {
                    showSalaryPerYear()
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMoreInfo()

        initSpinner()

        showContract()

        showLocation()

        moreInfo.setOnClickListener {
            mViewModel.mJobPresentation?.let {
                val detailsUrlIntent = it.redirectUrl?.let { redirectUrl -> newBrowserIntent(redirectUrl) }
                startActivity(detailsUrlIntent)
            }
        }

        salaryOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mViewModel.mSalaryOption.postValue(position)
            }
        }
    }

    override fun onDestroyView() {
        coroutineContext.cancelChildren()
        super.onDestroyView()
    }

    private fun initMoreInfo() {
        moreInfo.paintFlags = moreInfo.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun initSpinner() = launch {
        val spinnerAdapterDeferred = async(mDispatchers.io()) {
            ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayOf(PER_YEAR, PER_MONTH))
        }

        salaryOptions.adapter = spinnerAdapterDeferred.await().apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
    }

    private fun showSalaryPerMonth() = launch {
        var minSalary = withContext(mDispatchers.io()) {
            (mViewModel.mJobPresentation?.salaryMin?.div(12))?.toInt().toString()
        }
        var maxSalary = withContext(mDispatchers.io()) {
            (mViewModel.mJobPresentation?.salaryMax?.div(12))?.toInt().toString()
        }

        val stringSalary = withContext(mDispatchers.io()) {
            if (minSalary == "null" && maxSalary == "null") {
                mViewModel.mSalaryVisibility.postValue(false)
            } else if (minSalary == "null" && maxSalary != "null") {
                minSalary = "..."
                mViewModel.mSalaryVisibility.postValue(true)
            } else if (maxSalary == "null" && minSalary != "null") {
                maxSalary = "..."
                mViewModel.mSalaryVisibility.postValue(true)
            } else {
                mViewModel.mSalaryVisibility.postValue(true)
            }

            String.format(Locale.getDefault(), resources.getString(R.string.salary), minSalary, maxSalary, MONTH)
        }

        salary.text = stringSalary
    }

    private fun showSalaryPerYear() = launch {
        var minSalary = withContext(mDispatchers.io()) {
            (mViewModel.mJobPresentation?.salaryMin)?.toInt().toString()
        }
        var maxSalary = withContext(mDispatchers.io()) {
            (mViewModel.mJobPresentation?.salaryMax)?.toInt().toString()
        }

        val stringSalary = withContext(mDispatchers.io()) {
            if (minSalary == "null" && maxSalary == "null") {
                mViewModel.mSalaryVisibility.postValue(false)
            } else if (minSalary == "null" && maxSalary != "null") {
                minSalary = "..."
                mViewModel.mSalaryVisibility.postValue(true)
            } else if (maxSalary == "null" && minSalary != "null") {
                maxSalary = "..."
                mViewModel.mSalaryVisibility.postValue(true)
            } else {
                mViewModel.mSalaryVisibility.postValue(true)
            }

            String.format(Locale.getDefault(), resources.getString(R.string.salary), minSalary, maxSalary, YEAR)
        }

        salary.text = stringSalary
    }

    private fun showContract() = launch(mDispatchers.io()) {
        var contractType = withContext(mDispatchers.io()) {
            mViewModel.mJobPresentation?.contractType
        }
        var contractTime = withContext(mDispatchers.io()) {
            mViewModel.mJobPresentation?.contractTime
        }

        val stringContract = withContext(mDispatchers.io()) {
            if (contractType == "null" && contractTime == "null") {
                mViewModel.mContractVisibility.postValue(false)
            } else if (contractType == "null" && contractTime != "null") {
                contractType = "..."
                mViewModel.mSalaryVisibility.postValue(true)
            } else if (contractTime == "null" && contractType != "null") {
                contractTime = "..."
                mViewModel.mSalaryVisibility.postValue(true)
            } else {
                mViewModel.mSalaryVisibility.postValue(true)
            }

            String.format(Locale.getDefault(), resources.getString(R.string.contract), contractType, contractTime)
        }

        mViewModel.mContract.postValue(stringContract)
    }

    private fun showLocation() {
        var location = ""

        mViewModel.mJobPresentation?.location?.area?.let {
            for ((index, value) in it.withIndex()) location += if (index == it.lastIndex) value else "$value, "
        }

        mViewModel.mLocation.postValue(location)
    }

    companion object {
        const val TAG = "JobDetailsFragment"

        const val ID = R.id.jobDetailsFragment

        private const val KEY_JOB = "JOB"

        private const val YEAR = "year"
        private const val MONTH = "month"

        private const val PER_YEAR = "per year"
        private const val PER_MONTH = "per month"

        fun newInstance() = JobDetailsFragment()

        fun newInstance(job: JobPresentation): JobDetailsFragment {
            val fragment = JobDetailsFragment.newInstance()
            val args = Bundle()
            args.putParcelable(KEY_JOB, job)
            fragment.arguments = args

            return fragment
        }
    }

}
