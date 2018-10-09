package mn.factory.androidvsu.ui.adapter.rv

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import mn.factory.androidvsu.model.adzuna.JobPresentation
import mn.factory.androidvsu.model.adzuna.JobPresentationViewModel

/**
 * Created by Turkin A. on 07/10/2018.
 */

abstract class BaseRecyclerAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val clickSubject = PublishSubject.create<Any>()
    val clickObservable: Observable<Any> = clickSubject

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseViewHolder {
        val binding =
                DataBindingUtil
                        .inflate<ViewDataBinding>(LayoutInflater.from(p0.context), p1, p0, false)

        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(p0: BaseViewHolder, p1: Int) {
        val anyObject = getAnyObjectForPosition(p1)

        when (anyObject) {
            is JobPresentation -> {
                p0.bind(anyObject, clickSubject, JobPresentationViewModel(anyObject))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position) ?: -1
    }

    protected abstract fun getAnyObjectForPosition(position: Int): Any?

    @LayoutRes
    protected abstract fun getLayoutIdForPosition(position: Int): Int?

}