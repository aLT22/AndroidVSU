object PluginDependencies {
    val android = "com.android.tools.build:gradle:${Versions.gradleAndroidPlugin}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object ProjectModules {
    const val data = ":data"
    const val domain = ":domain"
}

object ProjectDependencies {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val ktx = "androidx.core:core-ktx:${Versions.ktx}"

    //AndroidX deps
    val supportAppCompatX = "androidx.appcompat:appcompat:${Versions.supportX}"
    val constraintLayoutX = "androidx.constraintlayout:constraintlayout:${Versions.constraintX}"
    val cardViewX = "androidx.cardview:cardview:${Versions.supportX}"
    val recyclerViewX = "androidx.recyclerview:recyclerview:${Versions.supportX}"
    val material = "com.google.android.material:material:${Versions.supportX}"

    //Koin AndroidX
    val koinAndroid = "org.koin:koin-android:${Versions.koinX}"
    val koinViewModelX = "org.koin:koin-androidx-viewmodel:${Versions.koinX}"
    val koinScopeX = "org.koin:koin-androidx-scope:${Versions.koinX}"

    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val rxJava2Adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"

    val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"

    val lifecycle = "android.arch.lifecycle:extensions:${Versions.lifecycle}"
    val lifecycleCompiler = "android.arch.lifecycle:compiler:${Versions.lifecycle}"
    val navigationFragment = "android.arch.navigation:navigation-fragment-ktx:${Versions.navigation}"
    val navigationUi = "android.arch.navigation:navigation-ui-ktx:${Versions.navigation}"

    val waveLoading = "com.race604.waveloading:library:${Versions.waveLoading}"
    val expandableLayout = "com.github.aakira:expandable-layout:${Versions.expandableLayout}"
    val multislider = "io.apptik.widget:multislider:${Versions.multislider}"
}

object TestDependencies {
    val jUnit = "junit:junit:${Versions.jUnit}"

    //Classic test deps
    val runner = "com.android.support.test:runner:${Versions.runner}"
    val espresso = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"

    //AndroidX test deps
    val runnerX = "androidx.test:runner:${Versions.runnerX}"
    val espressoX = "androidx.test.espresso:espresso-core:${Versions.espressoX}"
}

object Versions {
    const val gradleAndroidPlugin = "3.2.0"
    const val kotlin = "1.2.71"
    const val ktx = "1.0.0"

    const val compileSdk = 28
    const val targetSdk = 28
    const val minSdkVersion = 21
    const val versionCode = 1
    const val versionName = "1.0"

    const val jUnit = "4.12"
    //Classic test deps versions
    const val runner = "1.0.2"
    const val espresso = "3.0.2"

    //AndroidX test deps versions
    const val runnerX = "1.1.0"
    const val espressoX = "3.1.0"

    //AndroidX deps versions
    const val supportX = "1.0.0"
    const val constraintX = "1.1.2"

    const val koinX = "1.0.2"

    const val retrofit = "2.4.0"
    const val loggingInterceptor = "3.10.0"

    const val rxJava = "2.2.2"
    const val rxAndroid = "2.1.0"

    const val lifecycle = "1.1.1"
    const val navigation = "1.0.0-alpha07"

    const val waveLoading = "1.1.1"
    const val expandableLayout = "1.6.0@aar"
    const val multislider = "1.3"
}