package mn.factory.androidvsu.utils.exts

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Created by Turkin A. on 07/12/2018.
 */

/**
 * Intent extension block starts
 * */
inline fun <reified T : Any> newIntent(context: Context): Intent =
        Intent(context, T::class.java)

inline fun newBrowserIntent(url: String): Intent =
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
/**
 * Intent extension block ends
 * */