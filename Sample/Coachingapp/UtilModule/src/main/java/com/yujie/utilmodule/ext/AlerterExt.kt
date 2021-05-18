package com.yujie.utilmodule.ext

import android.app.Activity
import com.tapadoo.alerter.Alerter
import com.yujie.utilmodule.R

fun Activity.showErrorAlert() {
    Alerter.create(this)
        .setIcon(R.drawable.ic_error)
        .setIconColorFilter(0)
        .setTitle(getString(R.string.error_title))
        .setText(R.string.error_text)
        .setBackgroundColorRes(R.color.yellow_f7cd3b)
        .setDuration(5000)
        .show()
}

fun Activity.showNetworkAlert() {
    Alerter.create(this)
        .setIcon(R.drawable.ic_network)
        .setIconColorFilter(0)
        .setTitle(getString(R.string.network_title))
        .setText(R.string.network_text)
        .setBackgroundColorRes(R.color.orange_f09401)
        .setDuration(5000)
        .show()
}

fun Activity.showGoogleServiceAlert() {
    Alerter.create(this)
        .setIcon(R.drawable.ic_google_play)
        .setIconColorFilter(0)
        .setTitle(getString(R.string.google_play_service_title))
        .setText(R.string.google_play_service_text)
        .setBackgroundColorRes(R.color.blue_02abe2)
        .setDuration(5000)
        .show()
}

