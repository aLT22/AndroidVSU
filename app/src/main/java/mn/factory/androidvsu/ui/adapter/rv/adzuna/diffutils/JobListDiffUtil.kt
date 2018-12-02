package mn.factory.androidvsu.ui.adapter.rv.adzuna.diffutils

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import mn.factory.androidvsu.model.adzuna.job.JobPresentation
import mn.factory.androidvsu.model.adzuna.job.JobPresentationViewModel
import java.util.concurrent.Callable

/**
 * Created by Turkin A. on 17/10/2018.
 */
class JobListDiffUtil(
        private val jobs: List<JobPresentation>,
        private val items: List<JobPresentation>
) : DiffUtil.Callback(), Callable<DiffUtil.DiffResult> {

    override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
        val oldJob = jobs[p0]
        val newJob = items[p1]

        return oldJob.id == newJob.id
    }

    override fun getOldListSize(): Int = jobs.size

    override fun getNewListSize(): Int = items.size

    override fun areContentsTheSame(p0: Int, p1: Int): Boolean {
        val oldJob = jobs[p0]
        val newJob = items[p1]

        return oldJob.id == newJob.id &&
                oldJob.company == newJob.company &&
                oldJob.description == newJob.description &&
                oldJob.latitude == newJob.latitude &&
                oldJob.location == newJob.location &&
                oldJob.longitude == newJob.longitude &&
                oldJob.salaryMax == newJob.salaryMax &&
                oldJob.salaryMin == newJob.salaryMin &&
                oldJob.title == newJob.title
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldJob = jobs[oldItemPosition]
        val newJob = items[newItemPosition]
        val diffBundle = Bundle()

        if (oldJob.title != newJob.title) diffBundle.putString(JobPresentationViewModel.TITLE, newJob.title)
        if (oldJob.description != newJob.description) diffBundle.putString(JobPresentationViewModel.DESCRIPTION, newJob.description)
        if (oldJob.company != newJob.company) diffBundle.putString(JobPresentationViewModel.COMPANY_TITLE, newJob.company?.displayName ?: "")
        if (oldJob.latitude != newJob.latitude) diffBundle.putDouble(JobPresentationViewModel.LATITUDE, newJob.latitude ?: 0.0)
        if (oldJob.longitude != newJob.longitude) diffBundle.putDouble(JobPresentationViewModel.LONGITUDE, newJob.longitude ?: 0.0)
        if (oldJob.location != newJob.location) diffBundle.putString(JobPresentationViewModel.LOCATION, newJob.location?.displayName ?: "")
        if (oldJob.salaryMin != newJob.salaryMin) diffBundle.putDouble(JobPresentationViewModel.SALARY_MIN, newJob.salaryMin ?: 0.0)
        if (oldJob.salaryMax != newJob.salaryMax) diffBundle.putDouble(JobPresentationViewModel.SALARY_MAX, newJob.salaryMax ?: 0.0)
        if (oldJob.contractTime != newJob.contractTime) diffBundle.putString(JobPresentationViewModel.CONTRACT_TIME, newJob.contractTime ?: "")
        if (oldJob.contractType != newJob.contractType) diffBundle.putString(JobPresentationViewModel.CONTRACT_TYPE, newJob.contractType ?: "")
        if (oldJob.created != newJob.created) diffBundle.putString(JobPresentationViewModel.CREATED, newJob.created ?: "")

        if (diffBundle.size() == 0) return null

        return diffBundle
    }

    override fun call(): DiffUtil.DiffResult = DiffUtil.calculateDiff(this)

}