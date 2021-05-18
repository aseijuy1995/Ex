package tw.north27.coachingapp.ext

import android.content.Context
import android.os.Build
import tw.north27.coachingapp.util.ProcessLifeObs

/**
 * 啟動前後景
 * */
fun Context.startProcessLifeObs() = ProcessLifeObs(this)

/**
 * 版本名
 * */
val Context.versionName: String
    get() = packageManager.getPackageInfo(packageName, 0).versionName

/**
 * 版本號
 * */
val Context.versionCode: Long
    get() = packageManager.getPackageInfo(packageName, 0)!!.run {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) longVersionCode else versionCode.toLong()
    }