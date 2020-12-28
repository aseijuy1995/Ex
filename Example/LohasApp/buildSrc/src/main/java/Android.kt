object Android {

    const val compileSdk = 30
    const val buildTools = "29.0.3"

    //defaultConfig
    object DefaultConfig {
        const val ID = "edu.yujie.lohasapp"
        const val minSdk = 17
        const val targetSdk = 30
        const val versionCode = 1
        const val versionName = "1.0.0"
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