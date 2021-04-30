import org.gradle.api.JavaVersion

object Config {
    const val application_id = "com.joesemper.simpletranslator"
    const val compile_sdk = 29
    const val build_tools = "30.0.3"
    const val min_sdk = 21
    const val target_sdk = 29
    val java_version = JavaVersion.VERSION_1_8
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}

object Modules {
    const val app = ":app"
    const val core = ":core"
    const val model = ":model"
    const val repository = ":repository"
    const val utils = ":utils"
    //Features
    const val historyScreen = ":historyScreen"
}

object Versions {
    //Tools
    const val multidex = "1.0.3"

    //Design
    const val appcompat = "1.1.0-rc01"
    const val material = "1.3.0"
    const val swipeRefresh = "1.1.0"

    //Kotlin
    const val core = "1.0.2"
    const val stdlib = "1.3.41"
    const val coroutinesCore = "1.2.1"
    const val coroutinesAndroid = "1.1.1"

    //Retrofit
    const val retrofit = "2.6.0"
    const val converterGson = "2.6.0"
    const val interceptor = "3.12.1"
    const val adapterCoroutines = "0.9.2"

    //Koin
    const val koin = "2.0.1"

    //Glide
    const val glide = "4.9.0"

    //Picasso
    const val picasso = "2.5.2"

    //Room
    const val room = "2.2.0-alpha01"

    //Navigation
    const val navigation = "2.3.5"

    //JetpackCompose
    const val jetpack = "1.0.0-alpha10"

    //Test
    const val jUnit = "4.12"
    const val runner = "1.2.0"
    const val espressoCore = "3.2.0"
}

object Tools {
    const val multidex = "com.android.support:multidex:${Versions.multidex}"
}

object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val swipe_refresh =  "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.stdlib}"
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val adapter_coroutines =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.adapterCoroutines}"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}"
}

object Koin {
    const val koin_android = "org.koin:koin-android:${Versions.koin}"
    const val koin_view_model = "org.koin:koin-android-viewmodel:${Versions.koin}"
}

object Picasso {
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"
}

object Navigation {
    const val fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val dynamic_features_fragment = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation}"
    const val testing = "androidx.navigation:navigation-testing:${Versions.navigation}"
}

object JetpackCompose {
    const val navigation_compose = "androidx.navigation:navigation-compose:${Versions.jetpack}"
}

object TestImpl {
    const val junit = "junit:junit:${Versions.jUnit}"
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}


