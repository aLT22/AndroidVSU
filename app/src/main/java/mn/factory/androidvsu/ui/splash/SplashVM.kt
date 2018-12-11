package mn.factory.androidvsu.ui.splash

import android.annotation.SuppressLint
import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import mn.factory.androidvsu.R
import mn.factory.androidvsu.model.adzuna.mapper.VersionToVersionPresentationMapper
import mn.factory.androidvsu.utils.exts.extendedErrorMessage
import mn.factory.domain.adzuna.interactors.GetVersionInteractor

/**
 * Created by Turkin A. on 21/11/2018.
 */
class SplashVM(
        private val context: Application,
        private val getVersionInteractor: GetVersionInteractor,
        private val mapper: VersionToVersionPresentationMapper
) : AndroidViewModel(context) {

    val mVersion = MutableLiveData<String>()
    val mIsTimerFinished = MutableLiveData<Boolean>()

    var mCountDownTimer: CountDownTimer? = null

    private var isVersionLoaded = false

    init {
        fetchVersion()
        mCountDownTimer = object : CountDownTimer(5000, 1000) {
            override fun onFinish() {
                if (isVersionLoaded) mIsTimerFinished.postValue(true)
                else mIsTimerFinished.postValue(false)
            }

            override fun onTick(millisUntilFinished: Long) {
                Log.d(TAG, millisUntilFinished.toString())
            }

        }
        mCountDownTimer?.start()
    }

    @SuppressLint("CheckResult")
    fun fetchVersion() {
        getVersionInteractor
                .execute()
                .map { mapper.map(it) }
                .extendedErrorMessage()
                .subscribe({ version ->
                    val info = String.format(context.getString(R.string.adzuna_version), version.apiVersion, version.softwareVersion)
                    mVersion.postValue(info)
                    isVersionLoaded = true
                }, { throwable ->
                    throwable.printStackTrace()
                    isVersionLoaded = true
                    mVersion.postValue("Couldn't fetch info")
                })
    }

    override fun onCleared() {
        mCountDownTimer?.cancel()
        super.onCleared()
    }

    companion object {
        const val TAG = "SplashVM"
    }

}