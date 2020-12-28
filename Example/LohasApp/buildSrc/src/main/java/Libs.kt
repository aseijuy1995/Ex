object Libs {
    object Jetbrains {
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.jetBrains}"

        //coroutines
        const val coroutinesCoreLib = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutinesAndroidLib = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Google {

        //material
        const val materialLib = "com.google.android.material:material:${Versions.android}"

        //gson
        const val gsonLib = "com.google.code.gson:gson:${Versions.gson}"

        //proto
        const val protoLib = "com.google.protobuf:protobuf-javalite:${Versions.proto}"
        const val protobufLib = "com.google.protobuf:protoc:${Versions.proto}"

        //hilt
        const val hiltLib = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hiltCompilerLib = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    }

    object Android {
        const val multidexLib = "com.android.support:multidex:${Versions.multidex}"
    }

    object AndroidX {
        //ktx
        const val coreLib = "androidx.core:core-ktx:${Versions.core}"
        const val collectionLib = "androidx.collection:collection-ktx:${Versions.collection}"
        const val paletteKtxLib = "androidx.palette:palette-ktx:${Versions.palette}"
        const val sqLiteKtxLib = "androidx.sqlite:sqlite-ktx:${Versions.sqlite}"

        //appcompat
        const val appcompatLib = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val appcompatResourcesLib = "androidx.appcompat:appcompat-resources:${Versions.appcompat}"

        //activity
        const val activityKtxLib = "androidx.activity:activity-ktx:${Versions.activity}"

        //fragment
        const val fragmentKtxLib = "androidx.fragment:fragment-ktx:${Versions.fragment}"
        const val fragmentTestingLib = "androidx.fragment:fragment-testing:${Versions.fragment}"

        //app start up
        const val appStartUpLib = "androidx.startup:startup-runtime:${Versions.appStartUp}"

        //data store
        const val dataStorePrefLib = "androidx.datastore:datastore-preferences:${Versions.dataStore}"
        const val dataStoreCoreLib = "androidx.datastore:datastore-core:${Versions.dataStore}"

        //lifecycle
        const val viewModelKtxLib = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val liveDataKtxLib = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        const val lifecycleRuntimeLib = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        const val lifecycleCommonLib = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"//DefaultLifecycleObserver
        const val viewModelSavedStateLib = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
        const val lifecycleServiceLib = "androidx.lifecycle:lifecycle-service:${Versions.lifecycle}"
        const val lifecycleProcessLib = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"
        const val lifecycleReactiveStreamsLib = "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.lifecycle}"

        //paging
        const val pagingRuntimeKtxLib = "androidx.paging:paging-runtime-ktx:${Versions.paging}"
        const val pagingGuavaLib = "androidx.paging:paging-guava:${Versions.paging}"

        //room
        const val roomRuntimeLib = "androidx.room:room-runtime:${Versions.room}"
        const val roomKtxLib = "androidx.room:room-ktx:${Versions.room}"
        const val roomCoroutinesLib = "androidx.room:room-coroutines:${Versions.room}"
        const val roomCompilerLib = "androidx.room:room-compiler:${Versions.room}"

        //workManager
        const val workRunTimeKtxLib = "androidx.work:work-runtime-ktx:${Versions.work}"

        //navigation
        const val navRuntimeKtxLib = "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"
        const val navFragmentKtxLib = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val navUiKtxLib = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"//Unused
        const val navDynamicFeaturesFragmentLib = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation}"

        //hilt
        const val hiltViewModelLib = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltInject}"
        const val hiltWorkManagerLib = "androidx.hilt:hilt-work:${Versions.hiltInject}"
        const val hiltCompilerLib = "androidx.hilt:hilt-compiler:${Versions.hiltInject}"

        //constraintLayout
        const val constraintLayoutLib = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

        //swipeRefreshLayoutLib
        const val swipeRefreshLayoutLib = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"


    }

    object ReactiveX {
        //rxJava
        const val rxJavaLib = "io.reactivex.rxjava3:rxjava:${Versions.rxJava}"

        //rxAndroid
        const val rxAndroidLib = "io.reactivex.rxjava3:rxandroid:${Versions.rxAndroid}"

        //rxKotlin
        const val rxKotlinLib = "io.reactivex.rxjava3:rxkotlin:${Versions.rxKotlin}"
    }

    object Jakewharton {
        //rxBinding
        const val rxBindingLib = "com.jakewharton.rxbinding4:rxbinding:${Versions.rxBinding}"
        const val rxBindingCoreLib = "com.jakewharton.rxbinding4:rxbinding-core:${Versions.rxBinding}"
        const val rxBindingAppcompatLib = "com.jakewharton.rxbinding4:rxbinding-appcompat:${Versions.rxBinding}"
        const val rxBindingDrawerLayoutLib = "com.jakewharton.rxbinding4:rxbinding-drawerlayout:${Versions.rxBinding}"
        const val rxBindingLeanbackLib = "com.jakewharton.rxbinding4:rxbinding-leanback:${Versions.rxBinding}"//min 17
        const val rxBindingRecyclerViewLib = "com.jakewharton.rxbinding4:rxbinding-recyclerview:${Versions.rxBinding}"
        const val rxBindingSlidingPaneLayoutLib = "com.jakewharton.rxbinding4:rxbinding-slidingpanelayout:${Versions.rxBinding}"
        const val rxBindingSwipereFreshLayoutLib = "com.jakewharton.rxbinding4:rxbinding-swiperefreshlayout:${Versions.rxBinding}"
        const val rxBindingViewPagerLib = "com.jakewharton.rxbinding4:rxbinding-viewpager:${Versions.rxBinding}"
        const val rxBindingViewPager2Lib = "com.jakewharton.rxbinding4:rxbinding-viewpager2:${Versions.rxBinding}"
        const val rxBindingMaterialLib = "com.jakewharton.rxbinding4:rxbinding-material:${Versions.rxBinding}"

        //rxRelay
        const val rxRelayLib = "com.jakewharton.rxrelay3:rxrelay:${Versions.rxRelay}"
    }

    object Trello {
        //rxLifecycle
        const val rxLifecycleLib = "com.trello.rxlifecycle4:rxlifecycle:${Versions.rxLifecycle}"
        const val rxLifecycleAndroidLib = "com.trello.rxlifecycle4:rxlifecycle-android:${Versions.rxLifecycle}"
        const val rxLifecycleComponentsLib = "com.trello.rxlifecycle4:rxlifecycle-components:${Versions.rxLifecycle}"
        const val rxLifecyclePreferenceLib = "com.trello.rxlifecycle4:rxlifecycle-components-preference:${Versions.rxLifecycle}"
        const val rxLifecycleAndroidLifecycleLib = "com.trello.rxlifecycle4:rxlifecycle-android-lifecycle:${Versions.rxLifecycle}"
        const val rxLifecycleKotlinLib = "com.trello.rxlifecycle4:rxlifecycle-kotlin:${Versions.rxLifecycle}"
        const val rxLifecycleAndroidLifecycleKotlinLib = "com.trello.rxlifecycle4:rxlifecycle-android-lifecycle-kotlin:${Versions.rxLifecycle}"
    }

    object Tbruyelle {
        //rxPermission
        const val rxPermissionsLib = "com.github.tbruyelle:rxpermissions:${Versions.rxPermissions}"
    }

    object Ssseasonnn {
        //rxDownload
        const val rxDownloadLib = "com.github.ssseasonnn.RxDownload:rxdownload4:${Versions.rxDownload}"
        const val rxDownloadManagerLib = "com.github.ssseasonnn.RxDownload:rxdownload4-manager:${Versions.rxDownload}"
        const val rxDownloadNotificationLib = "com.github.ssseasonnn.RxDownload:rxdownload4-notification:${Versions.rxDownload}"
        const val rxDownloadRecorderLib = "com.github.ssseasonnn.RxDownload:rxdownload4-recorder:${Versions.rxDownload}"
    }

    object Squareup {
        //okhttp
        const val okHttpLib = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val okHttpMockWebServerLib = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"
        const val okHttpLoggingInterceptorLib = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

        //okio
        const val okioLib = "com.squareup.okio:okio:${Versions.okio}"

        //retrofit
        const val retrofitLib = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofitConverterGsonLib = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val retrofitMockLib = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}"
    }

    object Koin {
        const val koinCoreLib = "org.koin:koin-core:${Versions.koin}"
        const val koinCoreExtLib = "org.koin:koin-core-ext:${Versions.koin}"
        const val koinScopeLib = "org.koin:koin-androidx-scope:${Versions.koin}"
        const val koinViewModelLib = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
        const val koinFragmentLib = "org.koin:koin-androidx-fragment:${Versions.koin}"
    }

    object BumpTech {
        const val glideLib = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val glideOkHttpIntegrationLib = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"
        const val glideCompilerLib = "com.github.bumptech.glide:compiler:${Versions.glide}"
    }

    object Orhanobut {
        //Logger
        const val loggerLib = "com.orhanobut:logger:${Versions.logger}"
    }

    object Facebook {
        const val stethoLib = "com.facebook.stetho:stetho:${Versions.stetho}"
        const val shimmerLib = "com.facebook.shimmer:shimmer:${Versions.shimmer}"
    }

    object Qianwen{
        const val updateAppJavaLib = "com.qianwen:update-app:${Versions.updateAppJava}"
        const val updateAppKotlinLib = "com.qianwen:update-app-kotlin:${Versions.updateAppKotlin}"
    }

    object Joery {
        const val animatedbottombarLib = "nl.joery.animatedbottombar:library:${Versions.animatedbottombar}"
    }

    object Ethanhua {
        const val skeletonLib = "com.ethanhua:skeleton:${Versions.skeleton}"
        const val shimmerLayoutLib = "io.supercharge:shimmerlayout:${Versions.shimmerLayout}"
    }

    object Test {
        //core
        const val coreLib = "androidx.test:core:${Versions.core}"

        //jUnit
        const val jUnitLib = "junit:junit:${Versions.jUnit}"

        //mock
        const val mockitoLib = "org.mockito:mockito-core:${Versions.mockito}"

        //mockk
        const val mockkLib = "io.mockk:mockk:${Versions.mockk}"

        //coroutines
        const val coroutinesTestingLib = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

        //paging
        const val pagingCommonKtxLib = "androidx.paging:paging-common-ktx:${Versions.paging}"

        //room
        const val roomTestingLib = "androidx.room:room-testing:${Versions.room}"

        //hilt
        const val hiltTestLib = "com.google.dagger:hilt-android-testing:${Versions.hilt}"

        //okhttp
        const val okHttpMockWebServerTestingLib = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"

        //koin
        const val koinTestingLib = "org.koin:koin-test:${Versions.koin}"

    }

    object AndroidTest {
        //jUnit
        const val jUnitAndroidLib = "androidx.test.ext:junit:${Versions.jUnitAndroid}"

        //mockk
        const val mockkLib = "io.mockk:mockk-android:${Versions.hilt}"

        //espresso
        const val espressoLib = "androidx.test.espresso:espresso-core:${Versions.espresso}"

        //worker
        const val workTestingKtxLib = "androidx.work:work-testing:${Versions.work}"

        //navigation
        const val navTestingLib = "androidx.navigation:navigation-testing:${Versions.navigation}"

    }
}