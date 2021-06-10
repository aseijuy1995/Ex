package tw.north27.coachingapp.ext

import android.app.Activity
import com.yujie.utilmodule.util.UpdateApp
import versionCode
import versionName

/**
 * 版本名更新
 * */
fun Activity.updateApp(newVersionName: String) = UpdateApp.with(this, newVersionName, versionName)

/**
 * 版本號更新
 * */
fun Activity.updateApp(newVersionCode: Long) = UpdateApp.with(this, newVersionCode, versionCode)