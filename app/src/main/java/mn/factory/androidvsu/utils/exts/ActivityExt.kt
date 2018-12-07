package mn.factory.androidvsu.utils.exts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Turkin A. on 21/11/2018.
 */

/**
 * AppCompatActivity ext block starts
 * */
@SuppressLint("ObsoleteSdkInt")
inline fun <reified T : Any> AppCompatActivity.launchActivity(
        requestCode: Int = -1,
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
}

@SuppressLint("ObsoleteSdkInt")
inline fun <reified T : Any> AppCompatActivity.launchActivityAndFinishCurrent(
        requestCode: Int = -1,
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(this)
    intent.init()
    this.finish()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
}
/**
 * AppCompatActivity ext block ends
 * */