object PluginDependencies {
    val android = "com.android.tools.build:gradle:${Versions.gradleAndroidPlugin}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object ProjectModules {
    val data = ":data"
    val domain = ":domain"
}

object ProjectDependencies {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val ktx = "androidx.core:core-ktx:${Versions.ktx}"

    val supportAppCompatv7 = "com.android.support:appcompat-v7:${Versions.appcompat}"
    val constraintLayout = "com.android.support.constraint:constraint-layout:${Versions.constraintLayout}"
    val cardView = "com.android.support:cardview-v7:${Versions.appcompat}"
    val recyclerView = "com.android.support:recyclerview-v7:${Versions.appcompat}"

    val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    val koinAndroidViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
    val koinAndroidScope = "org.koin:koin-android-scope:${Versions.koin}"

    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val rxJava2Adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"

    val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"

    val lifecycle = "android.arch.lifecycle:extensions:${Versions.lifecycle}"
    val lifecycleCompiler = "android.arch.lifecycle:compiler:${Versions.lifecycle}"
    val navigationFragment = "android.arch.navigation:navigation-fragment:${Versions.navigation}"
    val navigationUi = "android.arch.navigation:navigation-ui:${Versions.navigation}"
}

object TestDependencies {
    val jUnit = "junit:junit:${Versions.jUnit}"
    val runner = "com.android.support.test:runner:${Versions.runner}"
    val espresso = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
}

object Versions {
    val gradleAndroidPlugin = "3.2.0"
    val kotlin = "1.2.71"
    val ktx = "1.0.0"

    val compileSdk = 28
    val targetSdk = 28
    val minSdkVersion = 21
    val versionCode = 1
    val versionName = "1.0"

    val jUnit = "4.12"
    val runner = "1.0.2"
    val espresso = "3.0.2"

    val appcompat = "28.0.0"
    val constraintLayout = "1.1.3"

    val dagger = "2.16"
    val javaxInject = "1"

    val koin = "1.0.1"

    val retrofit = "2.4.0"
    val loggingInterceptor = "3.10.0"

    val rxJava = "2.2.2"
    val rxAndroid = "2.1.0"

    val lifecycle = "1.1.1"
    val navigation = "1.0.0-alpha07"
}