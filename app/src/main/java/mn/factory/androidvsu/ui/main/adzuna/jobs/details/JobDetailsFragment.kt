package mn.factory.androidvsu.ui.main.adzuna.jobs.details


import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import mn.factory.androidvsu.R
import mn.factory.androidvsu.databinding.FragmentJobDetailsBinding
import mn.factory.androidvsu.model.adzuna.job.JobPresentation
import org.koin.android.ext.android.inject

class JobDetailsFragment : Fragment() {

    private val mViewModel: JobDetailsVM by inject()
    lateinit var mBinding: FragmentJobDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        arguments?.let {
            mViewModel.mJobPresentation = it[KEY_JOB] as JobPresentation
            mViewModel.title.value = mViewModel.mJobPresentation?.title
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

        mViewModel.title.observe(this, Observer {
            mBinding.description.setTitle(it)
        })
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
