/**
 * @author YuJie on 2020/10/22
 * @describe 說明
 * @param 參數
 */

object Android {

    const val compileSdk = 30
    const val buildTools = "30.0.3"

    //defaultConfig
    object DefaultConfig {
        const val ID = "com.north27.CoachingApp_android"
        const val minSdk = 23
        const val targetSdk = 30
        const val versionCode = 1
        const val versionName = "1.0"
        const val isMultiDex = true// method count > 65536
        const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    //buildTypes
    object Release {
        const val name = "release"
        const val isMinify = false
        const val proguardFileName = "proguard-android-optimize.txt"
        const val proguardFile = "proguard-rules.pro"
    }

    //buildFeatures
    //databinding
    const val isSupportDataBinding = true

    //viewBinding
    const val isSupportViewBinding = true

    //test
    const val isIncludeAndroidResources = true
}