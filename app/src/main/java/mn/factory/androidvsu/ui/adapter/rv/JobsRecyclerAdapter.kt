package mn.factory.androidvsu.ui.adapter.rv

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import mn.factory.androidvsu.R
import mn.factory.androidvsu.model.adzuna.JobPresentation
import mn.factory.domain.utils.RxSchedulers

/**
 * Created by Turkin A. on 07/10/2018.
 */
class JobsRecyclerAdapter(
        private val rxSchedulers: RxSchedulers
) : BaseRecyclerAdapter() {

    private var jobs: ArrayList<JobPresentation> = ArrayList()

    override fun getAnyObjectForPosition(position: Int): Any? = jobs[position]

    override fun getLayoutIdForPosition(position: Int): Int? = R.layout.item_job

    override fun getItemCount(): Int = jobs.size

    override fun setItems(items: List<*>) {
        setJobs(items)
    }

    @SuppressLint("CheckResult")
    fun setJobs(items: List<*>) {
        if (jobs.isEmpty()) {
            jobs.addAll(items as ArrayList<JobPresentation>)
            notifyItemRangeInserted(0, items.size)
        } else {
            jobs = items as ArrayList<JobPresentation>
            Observable
                    .fromCallable(JobListDiffUtil(jobs, items))
                    .subscribeOn(rxSchedulers.computation())
                    .observeOn(rxSchedulers.ui())
                    .subscribe(
                            {
                                it.dispatchUpdatesTo(this)
                            },
                            {
                                Log.d("setJobs: ", it.localizedMessage)
                            }
                    )
        }
    }
}