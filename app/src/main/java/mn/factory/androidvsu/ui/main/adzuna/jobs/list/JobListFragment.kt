package mn.factory.androidvsu.ui.main.adzuna.jobs.list


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_job_list.*
import mn.factory.androidvsu.BR
import mn.factory.androidvsu.R
import mn.factory.androidvsu.databinding.FragmentJobListBinding
import mn.factory.androidvsu.model.ItemPresentation
import mn.factory.androidvsu.model.adzuna.job.JobPresentation
import mn.factory.androidvsu.ui.adapter.rv.adzuna.jobs.JobsRecyclerAdapter
import mn.factory.androidvsu.ui.main.MainActivity
import mn.factory.androidvsu.utils.listeners.EndlessScrollListener
import org.koin.android.ext.android.inject

class JobListFragment : Fragment() {

    private val mViewModel: JobListVM by inject()
    lateinit var mBinding: FragmentJobListBinding
    private val mJobsAdapter: JobsRecyclerAdapter by inject()

    private var mEndlessScrollListener: EndlessScrollListener? = null

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

        mViewModel.getJobs().observe(this, Observer {
            mJobsAdapter.setJobs(it ?: emptyList<JobPresentation>())
        })

        mViewModel.loading.observe(this, Observer {
            mBinding.swipeRefreshLayout.isRefreshing = it!!
        })
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

        mJobsAdapter.clickObservable.subscribe(
                {
                    (activity as MainActivity).showFragment(ACTION_TO_DETAILS, sharedBundle(it))
                },
                {
                    Log.e(TAG, it.localizedMessage)
                }
        )

        swipeRefreshLayout.setOnRefreshListener {
            mEndlessScrollListener?.let {
                it.reset()
                mViewModel.resetRequest()
                mViewModel.fetchJobs(true)
            }
        }
    }

    private fun sharedBundle(jobPresentation: ItemPresentation): Bundle {
        val job = jobPresentation as JobPresentation

        val arguments = Bundle()
        arguments.putParcelable(KEY_JOB, job)

        return arguments
    }

    companion object {
        const val TAG = "JobListFragment"
        const val ACTION_TO_DETAILS = R.id.action_jobListFragment_to_jobDetailsFragment

        private const val KEY_JOB = "JOB"

        fun newInstance() = JobListFragment().apply { retainInstance = true }
    }

}
