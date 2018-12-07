package mn.factory.androidvsu.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.race604.drawable.wave.WaveDrawable
import kotlinx.android.synthetic.main.activity_splash.*
import mn.factory.androidvsu.BR
import mn.factory.androidvsu.R
import mn.factory.androidvsu.databinding.ActivitySplashBinding
import mn.factory.androidvsu.ui.main.MainActivity
import mn.factory.androidvsu.utils.exts.launchActivityAndFinishCurrent
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashActivity : AppCompatActivity() {

    private val mViewModel: SplashVM by viewModel()
    private lateinit var mBinding: ActivitySplashBinding

    private lateinit var mDrawable: WaveDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        mBinding.setVariable(BR.vm, mViewModel)
        mBinding.setLifecycleOwner(this)

        initLoadingView()

        mViewModel.mIsTimerFinished.observe(this, Observer {
            if (it) {
                launchActivityAndFinishCurrent<MainActivity> { }
            } else {
                mViewModel.mCountDownTimer?.cancel()
                mViewModel.mCountDownTimer?.start()
                mViewModel.fetchVersion()
            }
        })
    }

    override fun onStart() {
        super.onStart()

        mViewModel.mCountDownTimer?.start()
    }

    override fun onStop() {
        super.onStop()

        mViewModel.mCountDownTimer?.cancel()
    }

    private fun initLoadingView() {
        mDrawable = WaveDrawable(this, R.drawable.adzuna_logo)
        adzunaLogo.setImageDrawable(mDrawable)
        mDrawable.isIndeterminate = true
    }

    companion object {
        const val TAG = "EntryFragment"
    }
}
