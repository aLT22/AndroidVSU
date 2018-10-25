package mn.factory.androidvsu.ui.adapter.rv

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import mn.factory.androidvsu.R
import mn.factory.androidvsu.model.ItemPresentation
import mn.factory.androidvsu.model.adzuna.JobPresentation
import mn.factory.domain.utils.RxSchedulers
import java.util.*

/**
 * Created by Turkin A. on 07/10/2018.
 */
class JobsRecyclerAdapter(
        private val rxSchedulers: RxSchedulers
) : BaseRecyclerAdapter() {

    private var jobs: LinkedList<JobPresentation> = LinkedList()

    override fun getItemPresentationForPosition(position: Int): ItemPresentation? = jobs[position]

    override fun getLayoutIdForPosition(position: Int): Int? = R.layout.item_job

    override fun getItemCount(): Int = jobs.size

    override fun setItems(items: Collection<ItemPresentation>) {
        setJobs(items)
    }

    @SuppressLint("CheckResult")
    fun setJobs(items: Collection<ItemPresentation>) {
        if (jobs.isEmpty()) {
            jobs.addAll(items as Collection<JobPresentation>)
            notifyItemRangeInserted(0, items.size)
        } else {
            jobs = items as LinkedList<JobPresentation>
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