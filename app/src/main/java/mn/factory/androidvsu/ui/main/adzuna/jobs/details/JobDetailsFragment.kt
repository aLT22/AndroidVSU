package mn.factory.androidvsu.ui.main.adzuna.jobs.details


import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import mn.factory.androidvsu.R
import mn.factory.androidvsu.model.adzuna.job.JobPresentation
import org.koin.android.ext.android.inject

class JobDetailsFragment : Fragment() {

    private val mViewModel: JobDetailsViewModel by inject()
    lateinit var mBinding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_details, container, false)
        mBinding.setLifecycleOwner(this)
        mBinding.setVariable(BR.vm, mViewModel)

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            Log.e(TAG, (it[KEY_JOB] as? JobPresentation).toString())
        }
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
