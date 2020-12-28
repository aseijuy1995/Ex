import com.google.protobuf.gradle.builtins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.protoc

plugins {
    id(Plugins.application)
    kotlin(Plugins.Kotlin.android)
    id(Plugins.protoBuf)
    id(Plugins.safeargsKotlin)
    kotlin(Plugins.Kotlin.kapt)
    kotlin(Plugins.Kotlin.extensions)
}

android {
    compileSdkVersion(Android.compileSdk)
    buildToolsVersion(Android.buildTools)

    defaultConfig {
        applicationId = Android.DefaultConfig.ID
        minSdkVersion(Android.DefaultConfig.minSdk)
        targetSdkVersion(Android.DefaultConfig.targetSdk)
        versionCode = Android.DefaultConfig.versionCode
        versionName = Android.DefaultConfig.versionName
        multiDexEnabled = Android.DefaultConfig.isMultiDex
        testInstrumentationRunner = Android.DefaultConfig.testInstrumentationRunner
        //        //java
//        javaCompileOptions {
//            annotationProcessorOptions {
//                arguments += ["room.schemaLocation":
//                                      "$projectDir/schemas".toString()]
//            }
//        }
        //kotlin
//        kapt {
//            arguments {
//                arg("room.schemaLocation", "$projectDir/schemas")
//            }
//        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        dataBinding = Android.isSupportDataBinding
        viewBinding = Android.isSupportViewBinding
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    //test
    testOptions {
        unitTests {
            isIncludeAndroidResources = Android.isIncludeAndroidResources
        }
    }
    //room
//    sourceSets {
//        getByName("androidTest") {
//            assets.srcDirs(files(projectDir, "schemas"))
//        }
//    }
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    //Jetbrains
    implementation(Libs.Jetbrains.kotlinStdLib)
    // coroutines
    implementation(Libs.Jetbrains.coroutinesCoreLib)
    implementation(Libs.Jetbrains.coroutinesAndroidLib)
    //--------------------------------------------------------------------------------
    //Google
    implementation(Libs.Google.materialLib)
    //gson
    implementation(Libs.Google.gsonLib)
    //proto
    implementation(Libs.Google.protoLib)
    //hilt
    implementation(Libs.Google.hiltLib)
    kapt(Libs.Google.hiltCompilerLib)
    //--------------------------------------------------------------------------------
    //Android
    implementation(Libs.Android.multidexLib)
    //--------------------------------------------------------------------------------
    //AndroidX
    // ktx
    implementation(Libs.AndroidX.coreLib)
    implementation(Libs.AndroidX.collectionLib)
    implementation(Libs.AndroidX.paletteKtxLib)
    implementation(Libs.AndroidX.sqLiteKtxLib)
    // appcompat
    implementation(Libs.AndroidX.appcompatLib)
    implementation(Libs.AndroidX.appcompatResourcesLib)
    // activity
    implementation(Libs.AndroidX.activityKtxLib)
    // fragment
    implementation(Libs.AndroidX.fragmentKtxLib)
    debugImplementation(Libs.AndroidX.fragmentTestingLib)
    // app start up
    implementation(Libs.AndroidX.appStartUpLib)
    // data store
    implementation(Libs.AndroidX.dataStorePrefLib)
    implementation(Libs.AndroidX.dataStoreCoreLib)
    // lifecycle
    implementation(Libs.AndroidX.viewModelKtxLib)
    implementation(Libs.AndroidX.liveDataKtxLib)
    implementation(Libs.AndroidX.lifecycleRuntimeLib)
    implementation(Libs.AndroidX.lifecycleCommonLib)
    implementation(Libs.AndroidX.viewModelSavedStateLib)
    implementation(Libs.AndroidX.lifecycleServiceLib)
    implementation(Libs.AndroidX.lifecycleProcessLib)
    implementation(Libs.AndroidX.lifecycleReactiveStreamsLib)
    // paging
    implementation(Libs.AndroidX.pagingRuntimeKtxLib)
//    implementation(Libs.pagingGuavaLib)
    // room
    implementation(Libs.AndroidX.roomRuntimeLib)
    implementation(Libs.AndroidX.roomKtxLib)
//    implementation(Libs.AndroidX.roomCoroutinesLib)
    kapt(Libs.AndroidX.roomCompilerLib)
    // work manager
    implementation(Libs.AndroidX.workRunTimeKtxLib)
    // navigation
    implementation(Libs.AndroidX.navRuntimeKtxLib)
    implementation(Libs.AndroidX.navFragmentKtxLib)
    implementation(Libs.AndroidX.navUiKtxLib)
    implementation(Libs.AndroidX.navDynamicFeaturesFragmentLib)
    // hilt - viewModel & workManager
    implementation(Libs.AndroidX.hiltViewModelLib)
    implementation(Libs.AndroidX.hiltWorkManagerLib)
    kapt(Libs.AndroidX.hiltCompilerLib)
    // constraintLayout
    implementation(Libs.AndroidX.constraintLayoutLib)
    // swipeRefreshLayoutLib
    implementation(Libs.AndroidX.swipeRefreshLayoutLib)
    //--------------------------------------------------------------------------------
    //ReactiveX
    // rxJava
    implementation(Libs.ReactiveX.rxJavaLib)
    // rxAndroid
    implementation(Libs.ReactiveX.rxAndroidLib)
    // rxKotlin
    implementation(Libs.ReactiveX.rxKotlinLib)
    //--------------------------------------------------------------------------------
    //RxBinding
    implementation(Libs.Jakewharton.rxBindingLib)
    implementation(Libs.Jakewharton.rxBindingCoreLib)
    implementation(Libs.Jakewharton.rxBindingAppcompatLib)
    implementation(Libs.Jakewharton.rxBindingDrawerLayoutLib)
    implementation(Libs.Jakewharton.rxBindingLeanbackLib)//min17
    implementation(Libs.Jakewharton.rxBindingRecyclerViewLib)
    implementation(Libs.Jakewharton.rxBindingSlidingPaneLayoutLib)
    implementation(Libs.Jakewharton.rxBindingSwipereFreshLayoutLib)
//    implementation(Libs.Jakewharton.rxBindingViewPagerLib)
    implementation(Libs.Jakewharton.rxBindingViewPager2Lib)
    implementation(Libs.Jakewharton.rxBindingMaterialLib)
    //RxRelay
    implementation(Libs.Jakewharton.rxRelayLib)
    //--------------------------------------------------------------------------------
    //RxLifecycle
    implementation(Libs.Trello.rxLifecycleLib)
    implementation(Libs.Trello.rxLifecycleAndroidLib)
    implementation(Libs.Trello.rxLifecycleComponentsLib)
    implementation(Libs.Trello.rxLifecyclePreferenceLib)
    implementation(Libs.Trello.rxLifecycleAndroidLifecycleLib)
    implementation(Libs.Trello.rxLifecycleKotlinLib)
    implementation(Libs.Trello.rxLifecycleAndroidLifecycleKotlinLib)
    //--------------------------------------------------------------------------------
    //RxPermission
    implementation(Libs.Tbruyelle.rxPermissionsLib)
    //--------------------------------------------------------------------------------
    //RxDownload
    implementation(Libs.Ssseasonnn.rxDownloadLib)
    implementation(Libs.Ssseasonnn.rxDownloadManagerLib)
    implementation(Libs.Ssseasonnn.rxDownloadNotificationLib)
    implementation(Libs.Ssseasonnn.rxDownloadRecorderLib)
    //--------------------------------------------------------------------------------
    //Squareup
    // okhttp3
    implementation(Libs.Squareup.okHttpLib)
    implementation(Libs.Squareup.okHttpMockWebServerLib)
    implementation(Libs.Squareup.okHttpLoggingInterceptorLib)
    // okio
    implementation(Libs.Squareup.okioLib)
    // retrofit
    implementation(Libs.Squareup.retrofitLib)
    implementation(Libs.Squareup.retrofitConverterGsonLib)
    implementation(Libs.Squareup.retrofitMockLib)
    //--------------------------------------------------------------------------------
    //Koin
    implementation(Libs.Koin.koinCoreLib)
    implementation(Libs.Koin.koinCoreExtLib)
    implementation(Libs.Koin.koinScopeLib)
    implementation(Libs.Koin.koinViewModelLib)
    implementation(Libs.Koin.koinFragmentLib)
    //--------------------------------------------------------------------------------
    //BumpTech
    implementation(Libs.BumpTech.glideLib)
    implementation(Libs.BumpTech.glideOkHttpIntegrationLib)
    kapt(Libs.BumpTech.glideCompilerLib)
    //--------------------------------------------------------------------------------
    //logger
    implementation(Libs.Orhanobut.loggerLib)
    //Facebook
    implementation(Libs.Facebook.stethoLib)//https://www.himmy.cn/2019/07/06/android-%E4%BD%BF%E7%94%A8stetho%E5%9C%A8chrome%E6%B5%8F%E8%A7%88%E5%99%A8%E6%9F%A5%E7%9C%8Bsqlite%E6%95%B0%E6%8D%AE%E5%BA%93/
    implementation(Libs.Facebook.shimmerLib)
    //upDateApp
    implementation(Libs.Qianwen.updateAppJavaLib)
    implementation(Libs.Qianwen.updateAppKotlinLib)
    //bottomnavigationview
    implementation(Libs.Joery.animatedbottombarLib)
    implementation(Libs.Joery.animatedbottombarLib)
    //ethanhua
    implementation(Libs.Ethanhua.skeletonLib)
    implementation(Libs.Ethanhua.shimmerLayoutLib)
    //Test
    testImplementation(Libs.Test.coreLib)
    testImplementation(Libs.Test.jUnitLib)
    testImplementation(Libs.Test.mockitoLib)
    testImplementation(Libs.Test.mockkLib)
    testImplementation(Libs.Test.coroutinesTestingLib)
    testImplementation(Libs.Test.pagingCommonKtxLib)
    testImplementation(Libs.Test.roomTestingLib)
    testImplementation(Libs.Test.hiltTestLib)
    testImplementation(Libs.Test.okHttpMockWebServerTestingLib)
    testImplementation(Libs.Test.koinTestingLib)
    //AndroidTest
    androidTestImplementation(Libs.AndroidTest.jUnitAndroidLib)
    androidTestImplementation(Libs.AndroidTest.mockkLib)
    androidTestImplementation(Libs.AndroidTest.espressoLib)
    androidTestImplementation(Libs.AndroidTest.workTestingKtxLib)
    androidTestImplementation(Libs.AndroidTest.navTestingLib)

}

//data store - proto
// Generates the java Protobuf-lite code for the Protobufs in this project. See
// https://github.com/google/protobuf-gradle-plugin#customizing-protobuf-compilation
// for more information.

//Kotlin
protobuf.protobuf.run {
    protoc {
        artifact = Libs.Google.protobufLib
    }
    generateProtoTasks {
        all().forEach {
            it.builtins {
                java {
                    id("lite")
                }
            }
        }
    }
}