package mn.factory.androidvsu.ui.adapter.rv

import android.arch.lifecycle.ViewModel
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import com.android.databinding.library.baseAdapters.BR
import io.reactivex.subjects.PublishSubject
import mn.factory.androidvsu.databinding.ItemJobBinding
import mn.factory.androidvsu.model.ItemPresentation
import mn.factory.androidvsu.model.adzuna.JobPresentation
import mn.factory.androidvsu.model.adzuna.JobPresentationViewModel

/**
 * Created by Turkin A. on 07/10/2018.
 */
class BaseViewHolder(
        viewDataBinding: ViewDataBinding
) : RecyclerView.ViewHolder(
        viewDataBinding.root
) {
    private val mBinding = viewDataBinding
    lateinit var mViewModel: ViewModel

    //todo: add these kind of animation into the project
    //https://proandroiddev.com/implement-google-inbox-style-animation-on-android-18c261baeda6

    fun bind(anyObject: ItemPresentation?,
             publishSubject: PublishSubject<ItemPresentation>,
             viewModel: ViewModel,
             payloads: MutableList<Any>?) {
        when (anyObject) {
            is JobPresentation -> {
                bindJobs(anyObject, publishSubject, viewModel, payloads)
            }
            else -> {
            }
        }

        mBinding.executePendingBindings()
    }

    private fun bindJobs(job: JobPresentation,
                         publishSubject: PublishSubject<ItemPresentation>,
                         viewModel: ViewModel,
                         payloads: MutableList<Any>?) {
        mViewModel = viewModel as JobPresentationViewModel
        mBinding.setVariable(BR.vm, mViewModel)

        mBinding as ItemJobBinding
        mBinding.root.setOnClickListener { publishSubject.onNext(job) }

        //Needs for correct ellipsize working
        mBinding.title.isSelected = true
        mBinding.company.isSelected = true
        mBinding.location.isSelected = true

        /*payloads?.let {
            val jobPayloadsBundle = payloads[0] as Bundle
            for (key in jobPayloadsBundle.keySet()) {
                when (key) {
                    JobPresentationViewModel.TITLE -> {
                        mBinding.title.text = jobPayloadsBundle.getString(key)
                    }
                    JobPresentationViewModel.DESCRIPTION -> {
                        mBinding.description.text = jobPayloadsBundle.getString(key)
                    }
                    //TODO: check all fields
                }
            }
        }*/
    }
}