package mn.factory.androidvsu.ui.adapter.rv

import mn.factory.androidvsu.R
import mn.factory.androidvsu.model.adzuna.JobPresentation

/**
 * Created by Turkin A. on 07/10/2018.
 */
class JobsRecyclerAdapter : BaseRecyclerAdapter() {

    var jobs: List<JobPresentation> = ArrayList()

    override fun getAnyObjectForPosition(position: Int): Any? = jobs[position]

    override fun getLayoutIdForPosition(position: Int): Int? = R.layout.item_job

    override fun getItemCount(): Int = jobs.size
}