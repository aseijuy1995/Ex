package tw.north27.coachingapp.ext2

import android.app.Activity
import com.tapadoo.alerter.Alerter
import tw.north27.coachingapp.R

fun Activity.networkAlert() {
    Alerter.create(this)
        .setIcon(R.drawable.ic_baseline_network_wifi_24_white)
        .setTitle(getString(R.string.network_content))
        .setText(R.string.network_desc)
        .setBackgroundColor(R.color.red_e50014)
        .show()
}

fun Activity.errorAlert() {
    Alerter.create(this)
        .setIcon(R.drawable.ic_baseline_error_24_white)
        .setTitle(getString(R.string.error_content))
        .setText(R.string.error_desc)
        .setBackgroundColor(R.color.gray_666666)
        .show()
}

