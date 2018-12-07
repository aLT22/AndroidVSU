package mn.factory.androidvsu.ui.main

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import mn.factory.androidvsu.BR
import mn.factory.androidvsu.R
import mn.factory.androidvsu.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mViewModel: MainActivityVM by viewModel()
    private lateinit var mBinding: ActivityMainBinding

    private lateinit var mNavigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
        mBinding.setLifecycleOwner(this)
        mBinding.setVariable(BR.vm, mViewModel)

        mNavigationController = Navigation.findNavController(this, R.id.mainNavigationFragment)
        setupActionBarWithNavController(this, mNavigationController)
        NavigationUI.setupWithNavController(bottomNavigationView, mNavigationController)
    }

    override fun onSupportNavigateUp(): Boolean =
            Navigation.findNavController(this, R.id.mainNavigationFragment).navigateUp()

    fun showFragment(@IdRes actionId: Int, bundle: Bundle) {
        mNavigationController?.let { it.navigate(actionId, bundle) }
    }

    fun showFragment(@IdRes actionId: Int) {
        mNavigationController?.let { it.navigate(actionId) }
    }

    companion object {
        const val TAG = "MainActivity"
    }

}
