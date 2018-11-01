package mn.factory.androidvsu.ui.adapter.rv.adzuna.jobs

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import mn.factory.androidvsu.R
import mn.factory.androidvsu.model.ItemPresentation
import mn.factory.androidvsu.model.adzuna.job.JobPresentation
import mn.factory.androidvsu.ui.adapter.rv.BaseRecyclerAdapter
import mn.factory.androidvsu.ui.adapter.rv.adzuna.diffutils.JobListDiffUtil
import mn.factory.domain.utils.RxSchedulers

/**
 * Created by Turkin A. on 07/10/2018.
 */
class JobsRecyclerAdapter(
        private val rxSchedulers: RxSchedulers
) : BaseRecyclerAdapter() {

    private var jobs: ArrayList<JobPresentation> = ArrayList()

    override fun getItemPresentationForPosition(position: Int): ItemPresentation? = jobs[position]

    override fun getLayoutIdForPosition(position: Int): Int? = R.layout.item_job

    override fun getItemCount(): Int = jobs.size

    override fun setItems(items: Collection<ItemPresentation>) {
        setJobs(items)
    }

    @Suppress("UNCHECKED_CAST")
    @SuppressLint("CheckResult")
    fun setJobs(items: Collection<ItemPresentation>) {
        if (jobs.isEmpty()) {
            jobs.addAll(items as Collection<JobPresentation>)
            notifyItemRangeInserted(0, items.size)
        } else {
            Observable
                    .fromCallable(JobListDiffUtil(jobs, items as ArrayList<JobPresentation>))
                    .subscribeOn(rxSchedulers.computation())
                    .observeOn(rxSchedulers.ui())
                    .subscribe(
                            {
                                jobs.clear()
                                jobs.addAll(items)
                                it.dispatchUpdatesTo(this)
                            },
                            {
                                Log.d("setJobs: ", it.localizedMessage)
                            }
                    )
        }
    }
}