package mn.factory.androidvsu.ui.main

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import mn.factory.androidvsu.BR
import mn.factory.androidvsu.R
import mn.factory.androidvsu.databinding.ActivityMainBinding
import mn.factory.androidvsu.model.adzuna.JobPresentation
import mn.factory.androidvsu.ui.adapter.rv.JobsRecyclerAdapter
import mn.factory.androidvsu.utils.listener.EndlessScrollListener
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mViewModel: MainActivityViewModel by viewModel()
    lateinit var mBinding: ViewDataBinding
    private val mJobsAdapter: JobsRecyclerAdapter by inject()

    private var endlessScrollListener: EndlessScrollListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
        mBinding.setLifecycleOwner(this)
        mBinding.setVariable(BR.vm, mViewModel)

        //todo: change notifyDataSetChanded() method on DiffUtil.Callback
        mViewModel.jobsLiveData.observe(this, Observer {
            mJobsAdapter.jobs = it ?: emptyList()
            mJobsAdapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        })

        mJobsAdapter.clickObservable.subscribe {
            val jobPresentation = it as JobPresentation
            Toast.makeText(this, jobPresentation.id.toString(), Toast.LENGTH_SHORT).show()
        }

        jobList?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
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
                    mViewModel.fetchJobs()
                }
            }
            jobList.addOnScrollListener(endlessScrollListener as EndlessScrollListener)
        }

        endlessScrollListener?.let {
            it.loading.observe(this, Observer {
                swipeRefreshLayout.isRefreshing = it!!
            })
        }

        swipeRefreshLayout.setOnRefreshListener {
            endlessScrollListener?.let {
                it.resetState()
                (mJobsAdapter.jobs as ArrayList).clear()
                mViewModel.resetRequest()
                mViewModel.fetchJobs()
            }
        }
    }

    override fun onStop() {
        endlessScrollListener?.let { jobList.removeOnScrollListener(it) }
        endlessScrollListener = null
        super.onStop()
    }

}
