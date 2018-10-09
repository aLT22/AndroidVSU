package mn.factory.androidvsu

import android.app.Application
import mn.factory.androidvsu.di.*
import mn.factory.data.di.networkModule
import org.koin.android.ext.android.startKoin

/**
 * Created by Turkin A. on 05/10/2018.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(
                appModule,
                viewModule,
                repositoryModule,
                interactorsModule,
                mappersModule,
                networkModule
        ))
    }
}