package tw.north27.coachingapp.ext

import android.app.Activity
import tw.north27.coachingapp.util.UpdateApp

fun Activity.updateApp(versionName: String) = UpdateApp.with(this, versionName)

fun Activity.updateApp(versionCode: Long) = UpdateApp.with(this, versionCode)