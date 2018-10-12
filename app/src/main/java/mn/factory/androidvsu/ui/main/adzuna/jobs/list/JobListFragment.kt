package mn.factory.androidvsu.ui.main.adzuna.jobs.list


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
import mn.factory.androidvsu.model.adzuna.JobPresentation
import mn.factory.androidvsu.ui.adapter.rv.JobsRecyclerAdapter
import mn.factory.androidvsu.utils.listener.EndlessScrollListener
import org.koin.android.ext.android.inject

class JobListFragment : Fragment() {

    private val mViewModel: JobListViewModel by inject()
    lateinit var mBinding: FragmentJobListBinding
    private val mJobsAdapter: JobsRecyclerAdapter by inject()

    private var endlessScrollListener: EndlessScrollListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //todo: change notifyDataSetChanged() method on DiffUtil.Callback
        mViewModel.jobsLiveData.observe(this, Observer {
            mJobsAdapter.jobs.postValue(it ?: emptyList())
            mJobsAdapter.notifyDataSetChanged()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_list, container, false)
        mBinding.setLifecycleOwner(this)
        mBinding.setVariable(BR.vm, mViewModel)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mJobsAdapter.clickObservable.subscribe {
            val jobPresentation = it as JobPresentation
            Toast.makeText(activity, jobPresentation.id.toString(), Toast.LENGTH_SHORT).show()
        }

        jobList?.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = mJobsAdapter
            setHasFixedSize(true)
        }
    }

    override fun onStart() {
        super.onStart()

        jobList.layoutManager?.let {
            endlessScrollListener = object : EndlessScrollListener(it) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, recyclerView: RecyclerView) {
                    mViewModel.jobsInteractorRequest.page = page
                    mViewModel.fetchJobs(false)
                }
            }
            jobList.addOnScrollListener(endlessScrollListener as EndlessScrollListener)
        }

        endlessScrollListener?.let { it ->
            it.loading.observe(this, Observer {
                swipeRefreshLayout.isRefreshing = it!!
            })
        }

        swipeRefreshLayout.setOnRefreshListener {
            endlessScrollListener?.let {
                it.resetState()
                mViewModel.resetRequest()
                mViewModel.fetchJobs(true)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        mViewModel.loading.observe(this, Observer {
            mBinding.swipeRefreshLayout.isRefreshing = it!!
        })
    }

    override fun onStop() {
        endlessScrollListener?.let { jobList.removeOnScrollListener(it) }
        endlessScrollListener = null
        super.onStop()
    }

    companion object {
        const val TAG = "JobListFragment"

        const val PARAM_ONE_KEY = "PARAM_ONE_KEY"

        fun newInstance() = JobListFragment()

        fun newInstance(p1: Int): Fragment {
            val fragment = JobListFragment.newInstance()
            val bundle = Bundle()
            bundle.putInt(PARAM_ONE_KEY, p1)
            fragment.arguments = bundle

            return fragment
        }
    }

}
