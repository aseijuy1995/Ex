package tw.north27.coachingapp.ext

import android.content.Context
import tw.north27.coachingapp.util.ProcessLifeObs

/**
 * 啟動前後景
 * */
fun Context.startProcessLifeObs() = ProcessLifeObs(this)