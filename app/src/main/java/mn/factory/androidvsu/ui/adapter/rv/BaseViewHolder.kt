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

    fun bind(anyObject: Any?, publishSubject: PublishSubject<Any>, viewModel: ViewModel) {
        when (anyObject) {
            is JobPresentation -> {
                mViewModel = viewModel as JobPresentationViewModel
                mBinding.setVariable(BR.vm, mViewModel)

                mBinding as ItemJobBinding
                mBinding.root.setOnClickListener { publishSubject.onNext(anyObject) }
            }
            else -> {
            }
        }

        mBinding.executePendingBindings()
    }
}