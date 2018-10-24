package mn.factory.androidvsu.ui.main

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import mn.factory.androidvsu.BR
import mn.factory.androidvsu.R
import mn.factory.androidvsu.databinding.ActivityMainBinding
import mn.factory.androidvsu.ui.main.adzuna.jobs.list.JobListFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mViewModel: MainActivityViewModel by viewModel()
    private lateinit var mBinding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
        mBinding.setLifecycleOwner(this)
        mBinding.setVariable(BR.vm, mViewModel)

        setToolbarTitle()
        setJobListFragment()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) finish()
        else super.onBackPressed()
    }

    private fun setJobListFragment() {
        val jobListFragment = supportFragmentManager
                .findFragmentByTag(JobListFragment.TAG) as JobListFragment?
        if (jobListFragment == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, JobListFragment.newInstance(), JobListFragment.TAG)
                    .addToBackStack(JobListFragment.TAG)
                    .commit()
        }
    }

    private fun setToolbarTitle() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportFragmentManager.findFragmentById(R.id.fragmentContainer)?.let { checkApropriatetoolbarTitle(it) }
        supportFragmentManager.addOnBackStackChangedListener {
            supportFragmentManager.findFragmentById(R.id.fragmentContainer)?.let { checkApropriatetoolbarTitle(it) }
        }
    }

    private fun checkApropriatetoolbarTitle(fragment: Fragment) {
        when (fragment) {
            is JobListFragment -> supportActionBar?.title = TITLE_JOB_LIST_FRAGMENT
        }
    }

    companion object {
        private const val TITLE_JOB_LIST_FRAGMENT = "Jobs"
    }

}
