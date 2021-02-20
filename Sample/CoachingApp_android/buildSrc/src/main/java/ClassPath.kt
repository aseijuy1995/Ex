/**
 * @author YuJie on 2020/10/22
 * @describe 說明
 * @param 參數
 */
object ClassPath {
    const val tools = "com.android.tools.build:gradle:${Versions.tools}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.jetBrains}"

    //data store - proto
    const val protobuf = "com.google.protobuf:protobuf-gradle-plugin:${Versions.protoBufGradlePlugin}"

    //navigation safe args
    const val navigation = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"

    //hilt
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"

}