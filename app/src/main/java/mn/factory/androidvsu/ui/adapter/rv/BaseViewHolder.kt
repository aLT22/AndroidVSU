package mn.factory.androidvsu.ui.adapter.rv

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject
import mn.factory.androidvsu.databinding.ItemJobBinding
import mn.factory.androidvsu.model.ItemPresentation
import mn.factory.androidvsu.model.adzuna.job.JobPresentation
import mn.factory.androidvsu.model.adzuna.job.JobPresentationViewModel
import mn.factory.androidvsu.ui.adapter.rv.adzuna.jobs.JobsViewHolder

/**
 * Created by Turkin A. on 07/10/2018.
 */
open class BaseViewHolder(
        viewDataBinding: ViewDataBinding
) : RecyclerView.ViewHolder(
        viewDataBinding.root
) {
    private val mBinding = viewDataBinding

    @Suppress("CAST_NEVER_SUCCEEDS")
    fun bind(anyObject: ItemPresentation?,
             publishSubject: PublishSubject<ItemPresentation>,
             viewModel: ViewModel,
             payloads: MutableList<Any>?) {
        when (anyObject) {
            is JobPresentation -> {
                val jobsHolder = JobsViewHolder(mBinding as ItemJobBinding)
                jobsHolder.bind(anyObject, publishSubject, viewModel as JobPresentationViewModel, payloads)
            }
            else -> {
            }
        }

        mBinding.executePendingBindings()
    }
}