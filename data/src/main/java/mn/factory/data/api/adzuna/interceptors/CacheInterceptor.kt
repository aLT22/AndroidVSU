package mn.factory.data.api.adzuna.interceptors

import android.content.Context
import mn.factory.data.utils.hasNetwork
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Turkin A. on 07/11/2018.
 */
class CacheInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request =
                if (hasNetwork(context)!!) request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()

        return chain.proceed(request)
    }
}