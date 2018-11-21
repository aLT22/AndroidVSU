package mn.factory.androidvsu.ui.adapter.rv

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import mn.factory.androidvsu.model.ItemPresentation
import mn.factory.androidvsu.model.adzuna.job.JobPresentation
import mn.factory.androidvsu.model.adzuna.job.JobPresentationViewModel

/**
 * Created by Turkin A. on 07/10/2018.
 */

abstract class BaseRecyclerAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val clickSubject = PublishSubject.create<ItemPresentation>()
    val clickObservable: Observable<ItemPresentation> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): BaseViewHolder {
        val binding =
                DataBindingUtil
                        .inflate<ViewDataBinding>(LayoutInflater.from(parent.context), position, parent, false)

        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(p0: BaseViewHolder, p1: Int) {
        bindViewHolder(p0, p1, null)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, payloads: MutableList<Any>) {
        bindViewHolder(holder, position, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position) ?: -1
    }

    protected abstract fun getItemPresentationForPosition(position: Int): ItemPresentation?

    @LayoutRes
    protected abstract fun getLayoutIdForPosition(position: Int): Int?

    protected abstract fun setItems(items: Collection<ItemPresentation>)

    private fun bindViewHolder(holder: BaseViewHolder, position: Int, payloads: MutableList<Any>?) {
        val itemPresentation = getItemPresentationForPosition(position)

        when (itemPresentation) {
            is JobPresentation -> {
                holder.bind(itemPresentation, clickSubject, JobPresentationViewModel(itemPresentation), payloads)
            }
        }
    }

}