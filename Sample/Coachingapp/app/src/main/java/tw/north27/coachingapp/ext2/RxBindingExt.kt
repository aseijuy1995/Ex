package tw.north27.coachingapp.ext2

import android.view.View
import com.jakewharton.rxbinding4.view.clicks
import java.util.concurrent.TimeUnit


fun View.clickThrottleFirst(windowDuration: Long = 500, unit: TimeUnit = TimeUnit.MILLISECONDS) = clicks().throttleFirst(windowDuration, unit)