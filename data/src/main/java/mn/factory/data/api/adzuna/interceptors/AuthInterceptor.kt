package mn.factory.data.api.adzuna.interceptors

import mn.factory.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Turkin A. on 05/10/2018.
 */
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("app_id", BuildConfig.AppId)
                .addQueryParameter("app_key", BuildConfig.AppKey)
                .addQueryParameter("content-type", BuildConfig.ContentType)
                .build()

        val requestBuilder = original.newBuilder()
                .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}