package mn.factory.androidvsu.ui.main.adzuna.jobs.details


import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import mn.factory.androidvsu.BR
import mn.factory.androidvsu.R
import mn.factory.androidvsu.databinding.FragmentJobDetailsBinding
import mn.factory.androidvsu.model.adzuna.job.JobPresentation
import org.koin.androidx.viewmodel.ext.android.viewModel

class JobDetailsFragment : Fragment() {

    private val mViewModel: JobDetailsVM by viewModel()
    lateinit var mBinding: FragmentJobDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        arguments?.let {
            mViewModel.mJobPresentation = it[KEY_JOB] as JobPresentation
            mViewModel.title.value = mViewModel.mJobPresentation?.title
            mViewModel.mDescription.postValue(Html.fromHtml(mViewModel.mJobPresentation?.description))
            mViewModel.mDescriptionString.postValue(mViewModel.mJobPresentation?.description)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_details, container, false)
        mBinding.setLifecycleOwner(this)
        mBinding.setVariable(BR.vm, mViewModel)

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val TAG = "JobDetailsFragment"
        const val ID = R.id.jobDetailsFragment

        private const val KEY_JOB = "JOB"

        fun newInstance() = JobDetailsFragment()

        fun newInstance(job: JobPresentation): JobDetailsFragment {
            val fragment = JobDetailsFragment.newInstance()
            val args = Bundle()
            args.putParcelable(KEY_JOB, job)
            fragment.arguments = args

            return fragment
        }
    }

}
