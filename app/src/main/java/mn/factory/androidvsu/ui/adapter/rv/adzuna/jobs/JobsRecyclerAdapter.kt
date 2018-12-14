package mn.factory.androidvsu.ui.adapter.rv.adzuna.jobs

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import mn.factory.androidvsu.R
import mn.factory.androidvsu.model.ItemPresentation
import mn.factory.androidvsu.model.adzuna.job.JobPresentation
import mn.factory.androidvsu.ui.adapter.rv.BaseRecyclerAdapter
import mn.factory.androidvsu.ui.adapter.rv.adzuna.diffutils.JobListDiffUtil
import mn.factory.androidvsu.utils.exts.extendedErrorMessage
import mn.factory.domain.utils.RxSchedulers
import kotlin.coroutines.CoroutineContext

/**
 * Created by Turkin A. on 07/10/2018.
 */
class JobsRecyclerAdapter(
        private val rxSchedulers: RxSchedulers
) : BaseRecyclerAdapter(), CoroutineScope {

    val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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
                    .extendedErrorMessage()
                    .subscribe(
                            {
                                jobs.clear()
                                it.dispatchUpdatesTo(this)
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