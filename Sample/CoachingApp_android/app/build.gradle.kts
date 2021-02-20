plugins {
    id(Plugins.application)
    kotlin(Plugins.Kotlin.android)
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


    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildTypes {
        getByName(Android.Release.name) {
            isMinifyEnabled = Android.Release.isMinify
            proguardFiles(
                getDefaultProguardFile(Android.Release.proguardFileName),
                Android.Release.proguardFile
            )
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    implementation(Libs.AndroidX.constraintLayoutLib)

     //appcompat
    implementation(Libs.AndroidX.appcompatLib)
    implementation(Libs.AndroidX.appcompatResourcesLib)
}