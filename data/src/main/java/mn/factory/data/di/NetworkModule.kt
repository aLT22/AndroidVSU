package mn.factory.data.di

import mn.factory.data.BuildConfig
import mn.factory.data.api.adzuna.AdzunaService
import mn.factory.data.utils.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Turkin A. on 05/10/2018.
 */

val networkModule = module {
    single { createOkHttpClient() }
    single { createRetrofit(get()) }
    single { createAdzunaService(get()) }
}

fun createOkHttpClient(): OkHttpClient {
    val okHttpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = OkHttpClient.Builder()
    clientBuilder.addInterceptor(AuthInterceptor())
    if (BuildConfig.DEBUG) {
        okHttpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(okHttpLoggingInterceptor)
    }
    return clientBuilder
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
}

fun createRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Properties.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

fun createAdzunaService(retrofit: Retrofit) = retrofit.create(AdzunaService::class.java)

object Properties {
    const val BASE_URL = "http://api.adzuna.com/v1/api/"
}