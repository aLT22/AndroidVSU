package mn.factory.androidvsu.ui.main

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import mn.factory.androidvsu.BR
import mn.factory.androidvsu.R
import mn.factory.androidvsu.databinding.ActivityMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mViewModel: MainActivityViewModel by viewModel()
    private lateinit var mBinding: ViewDataBinding

    private lateinit var mNavigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
        mBinding.setLifecycleOwner(this)
        mBinding.setVariable(BR.vm, mViewModel)

        mNavigationController = Navigation.findNavController(this, R.id.hostFragment)
    }

    fun showFragment(@IdRes actionId: Int) {
        mNavigationController?.let { it.navigate(actionId) }
    }

    companion object {
        const val TAG = "MainActivity"
    }

}
