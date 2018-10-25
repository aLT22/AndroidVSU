package mn.factory.androidvsu.ui.adapter.rv

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.android.databinding.library.baseAdapters.BR
import io.reactivex.subjects.PublishSubject
import mn.factory.androidvsu.databinding.ItemJobBinding
import mn.factory.androidvsu.model.ItemPresentation
import mn.factory.androidvsu.model.adzuna.job.JobPresentation
import mn.factory.androidvsu.model.adzuna.job.JobPresentationViewModel

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

    @SuppressLint("SetTextI18n")
    private fun bindJobs(job: JobPresentation,
                         publishSubject: PublishSubject<ItemPresentation>,
                         viewModel: ViewModel,
                         payloads: MutableList<Any>?) {
        mViewModel = viewModel as JobPresentationViewModel
        mBinding.setVariable(BR.vm, mViewModel)

        mBinding as ItemJobBinding
        mBinding.root.setOnClickListener { publishSubject.onNext(job) }
        bindSalary(mBinding, job.salaryMin, job.salaryMax)
        bindLocationCoordinates(mBinding, job.latitude, job.longitude)

        //Needs for correct ellipsize working
        mBinding.title.isSelected = true
        mBinding.company.isSelected = true
        mBinding.location.isSelected = true

        if (payloads != null && payloads.size != 0) {
            val jobPayloadsBundle = payloads[0] as Bundle
            for (key in jobPayloadsBundle.keySet()) {
                when (key) {
                    JobPresentationViewModel.TITLE -> {
                        mBinding.title.text = jobPayloadsBundle.getString(key)
                    }
                    JobPresentationViewModel.DESCRIPTION -> {
                        mBinding.description.text = jobPayloadsBundle.getString(key)
                    }
                    JobPresentationViewModel.COMPANY_TITLE -> {
                        mBinding.company.text = jobPayloadsBundle.getString(key)
                    }
                    JobPresentationViewModel.LATITUDE -> {
                        bindLocationCoordinates(mBinding, jobPayloadsBundle.getDouble(key), job.longitude)
                    }
                    JobPresentationViewModel.LONGITUDE -> {
                        bindLocationCoordinates(mBinding, job.latitude, jobPayloadsBundle.getDouble(key))
                    }
                    JobPresentationViewModel.LOCATION -> {
                        mBinding.location.text = jobPayloadsBundle.getString(key)
                    }
                    JobPresentationViewModel.SALARY_MIN -> {
                        bindSalary(mBinding, jobPayloadsBundle.getDouble(key), job.salaryMax)
                    }
                    JobPresentationViewModel.SALARY_MAX -> {
                        bindSalary(mBinding, job.salaryMax, jobPayloadsBundle.getDouble(key))
                    }
                }
            }
        }
    }

    private fun bindSalary(binding: ItemJobBinding,
                           salaryMin: Double?,
                           salaryMax: Double?) {
        val salaryPerMonthMin = salaryMin?.div(12)?.toInt().toString()
        val salaryPerMonthMax = salaryMax?.div(12)?.toInt().toString()

        if (salaryPerMonthMin == "null" && salaryPerMonthMax != "null") {
            binding.salary.visibility = View.VISIBLE
            binding.salary.text = "... - $salaryPerMonthMax"
        } else if (salaryPerMonthMin != "null" && salaryPerMonthMax == "null") {
            binding.salary.visibility = View.VISIBLE
            binding.salary.text = "$salaryPerMonthMin - ..."
        } else if (salaryPerMonthMin != "null" && salaryPerMonthMax != "null") {
            binding.salary.visibility = View.VISIBLE
            binding.salary.text = "$salaryPerMonthMin - $salaryPerMonthMax"
        } else {
            binding.salary.visibility = View.GONE
        }
    }

    private fun bindLocationCoordinates(binding: ItemJobBinding,
                                        latitude: Double?,
                                        longitude: Double?) {
        if (latitude == null || longitude == null) {
            binding.latitude.visibility = View.GONE
            binding.longitude.visibility = View.GONE
            binding.punson.visibility = View.GONE
            binding.location.visibility = View.GONE
        } else {
            binding.latitude.visibility = View.VISIBLE
            binding.longitude.visibility = View.VISIBLE
            binding.location.visibility = View.VISIBLE

            binding.latitude.text = latitude.toString()
            binding.longitude.text = longitude.toString()

            binding.punson.visibility = View.VISIBLE
        }
    }
}