/**
 * @author YuJie on 2020/10/12
 * @describe 說明
 * @param 參數
 */
object Libs {
    object Path{
        const val gradle = "com.android.tools.build:gradle:${Versions.Gradle.gradle}"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin.kotlinStdLib}"
    }

    object Jetbrains{
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Kotlin.kotlinStdLib}"
    }

    object AndroidX{
        const val coreKtx = "androidx.core:core-ktx:${Versions.AndroidX.coreKtx}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appCompat}"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraintlayout}"
    }

    object Test{
        const val junit = "junit:junit:${Versions.junit}"
        const val androidxJunit = "androidx.test.ext:junit:${Versions.androidxJunit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }
}