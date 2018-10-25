package mn.factory.androidvsu.ui.main.adzuna.jobs.list


import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.databinding.library.baseAdapters.BR
import kotlinx.android.synthetic.main.fragment_job_list.*
import mn.factory.androidvsu.R
import mn.factory.androidvsu.databinding.FragmentJobListBinding
import mn.factory.androidvsu.model.adzuna.job.JobPresentation
import mn.factory.androidvsu.ui.adapter.rv.adzuna.jobs.JobsRecyclerAdapter
import mn.factory.androidvsu.utils.listener.EndlessScrollListener
import org.koin.android.ext.android.inject

class JobListFragment : Fragment() {

    private val mViewModel: JobListViewModel by inject()
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
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = mJobsAdapter
            setHasFixedSize(true)
            layoutManager?.let {
                mEndlessScrollListener?.mLayoutManager = it
            }
            addOnScrollListener(mEndlessScrollListener as EndlessScrollListener)
        }

        mJobsAdapter.clickObservable.subscribe {
            val jobPresentation = it as JobPresentation
            Toast.makeText(activity, jobPresentation.id.toString(), Toast.LENGTH_SHORT).show()
        }

        swipeRefreshLayout.setOnRefreshListener {
            mEndlessScrollListener?.let {
                mViewModel.resetRequest()
                mViewModel.fetchJobs(true)
            }
        }
    }

    companion object {
        const val TAG = "JobListFragment"

        const val PARAM_ONE_KEY = "PARAM_ONE_KEY"

        fun newInstance() = JobListFragment().apply { retainInstance = true }
    }

}
