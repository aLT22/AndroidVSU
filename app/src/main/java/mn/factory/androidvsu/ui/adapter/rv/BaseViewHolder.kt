package mn.factory.androidvsu.ui.adapter.rv

import android.arch.lifecycle.ViewModel
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import com.android.databinding.library.baseAdapters.BR
import io.reactivex.subjects.PublishSubject
import mn.factory.androidvsu.databinding.ItemJobBinding
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

    fun bind(anyObject: Any?, publishSubject: PublishSubject<Any>, viewModel: ViewModel) {
        when (anyObject) {
            is JobPresentation -> {
                mViewModel = viewModel as JobPresentationViewModel
                mBinding.setVariable(BR.vm, mViewModel)

                mBinding as ItemJobBinding
                mBinding.root.setOnClickListener { publishSubject.onNext(anyObject) }

                //Needs for correct ellipsize working
                mBinding.title.isSelected = true
                mBinding.company.isSelected = true
                mBinding.location.isSelected = true
            }
            else -> {
            }
        }

        mBinding.executePendingBindings()
    }
}