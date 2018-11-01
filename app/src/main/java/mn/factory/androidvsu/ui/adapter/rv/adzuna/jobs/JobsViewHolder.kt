package mn.factory.androidvsu.ui.adapter.rv.adzuna.jobs

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import io.reactivex.subjects.PublishSubject
import mn.factory.androidvsu.BR
import mn.factory.androidvsu.databinding.ItemJobBinding
import mn.factory.androidvsu.model.ItemPresentation
import mn.factory.androidvsu.model.adzuna.job.JobPresentation
import mn.factory.androidvsu.model.adzuna.job.JobPresentationViewModel
import mn.factory.androidvsu.ui.adapter.rv.BaseViewHolder
import java.util.*

/**
 * Created by Turkin A. on 25/10/2018.
 */
class JobsViewHolder(
        private val itemJobBinding: ItemJobBinding
) : BaseViewHolder(itemJobBinding) {

    //todo: add these kind of animation into the project
    //https://proandroiddev.com/implement-google-inbox-style-animation-on-android-18c261baeda6

    @SuppressLint("SetTextI18n")
    fun bind(job: JobPresentation,
             publishSubject: PublishSubject<ItemPresentation>,
             viewModel: JobPresentationViewModel,
             payloads: MutableList<Any>?) {
        itemJobBinding.setVariable(BR.vm, viewModel)

        //Set listeners
        itemJobBinding.root.setOnClickListener { publishSubject.onNext(job) }
        itemJobBinding.punson.setOnClickListener { startMaps(job.latitude, job.longitude, job.company?.displayName) }
        itemJobBinding.latitude.setOnClickListener { startMaps(job.latitude, job.longitude, job.company?.displayName) }
        itemJobBinding.longitude.setOnClickListener { startMaps(job.latitude, job.longitude, job.company?.displayName) }
        itemJobBinding.location.setOnClickListener { startMaps(job.latitude, job.longitude, job.company?.displayName) }

        //Bind views
        bindSalary(job.salaryMin, job.salaryMax)
        bindLocationCoordinates(job.latitude, job.longitude)

        //Needs for correct ellipsize working
        itemJobBinding.title.isSelected = true
        itemJobBinding.company.isSelected = true
        itemJobBinding.location.isSelected = true

        applyPayloads(payloads, job)
    }

    private fun applyPayloads(payloads: MutableList<Any>?,
                              job: JobPresentation) {
        if (payloads != null && payloads.size != 0) {
            val jobPayloadsBundle = payloads[0] as Bundle
            for (key in jobPayloadsBundle.keySet()) {
                when (key) {
                    JobPresentationViewModel.TITLE -> {
                        itemJobBinding.title.text = jobPayloadsBundle.getString(key)
                    }
                    JobPresentationViewModel.DESCRIPTION -> {
                        itemJobBinding.description.text = jobPayloadsBundle.getString(key)
                    }
                    JobPresentationViewModel.COMPANY_TITLE -> {
                        itemJobBinding.company.text = jobPayloadsBundle.getString(key)
                    }
                    JobPresentationViewModel.LATITUDE -> {
                        bindLocationCoordinates(jobPayloadsBundle.getDouble(key), job.longitude)
                    }
                    JobPresentationViewModel.LONGITUDE -> {
                        bindLocationCoordinates(job.latitude, jobPayloadsBundle.getDouble(key))
                    }
                    JobPresentationViewModel.LOCATION -> {
                        itemJobBinding.location.text = jobPayloadsBundle.getString(key)
                    }
                    JobPresentationViewModel.SALARY_MIN -> {
                        bindSalary(jobPayloadsBundle.getDouble(key), job.salaryMax)
                    }
                    JobPresentationViewModel.SALARY_MAX -> {
                        bindSalary(job.salaryMax, jobPayloadsBundle.getDouble(key))
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindSalary(salaryMin: Double?,
                           salaryMax: Double?) {
        val salaryPerMonthMin = salaryMin?.div(12)?.toInt().toString()
        val salaryPerMonthMax = salaryMax?.div(12)?.toInt().toString()

        if (salaryPerMonthMin == "null" && salaryPerMonthMax != "null") {
            itemJobBinding.salary.visibility = View.VISIBLE
            itemJobBinding.salary.text = "... - $salaryPerMonthMax"
        } else if (salaryPerMonthMin != "null" && salaryPerMonthMax == "null") {
            itemJobBinding.salary.visibility = View.VISIBLE
            itemJobBinding.salary.text = "$salaryPerMonthMin - ..."
        } else if (salaryPerMonthMin != "null" && salaryPerMonthMax != "null") {
            itemJobBinding.salary.visibility = View.VISIBLE
            itemJobBinding.salary.text = "$salaryPerMonthMin - $salaryPerMonthMax"
        } else {
            itemJobBinding.salary.visibility = View.GONE
        }
    }

    private fun bindLocationCoordinates(latitude: Double?,
                                        longitude: Double?) {
        if (latitude == null || longitude == null) {
            itemJobBinding.latitude.visibility = View.GONE
            itemJobBinding.longitude.visibility = View.GONE
            itemJobBinding.punson.visibility = View.GONE
            itemJobBinding.location.visibility = View.GONE
        } else {
            itemJobBinding.latitude.visibility = View.VISIBLE
            itemJobBinding.longitude.visibility = View.VISIBLE
            itemJobBinding.location.visibility = View.VISIBLE

            itemJobBinding.latitude.text = latitude.toString()
            itemJobBinding.longitude.text = longitude.toString()

            itemJobBinding.punson.visibility = View.VISIBLE
        }
    }

    private fun startMaps(latitude: Double?,
                          longitude: Double?,
                          companyName: String?) {
        val uri = String.format(Locale.getDefault(), MAPS_PATTERN, latitude, longitude, latitude, longitude, companyName)
        val mapsIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        itemJobBinding.root.context.startActivity(mapsIntent)
    }

    companion object {
        private const val MAPS_PATTERN = "geo:%f,%f?q=%f,%f(%s)"
    }

}