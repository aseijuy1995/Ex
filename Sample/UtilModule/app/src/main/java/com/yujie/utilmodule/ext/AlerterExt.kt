//package com.yujie.utilmodule.ext
//
//import android.app.Activity
//import com.tapadoo.alerter.Alerter
//import com.yujie.utilmodule.R
//
//fun Activity.showErrorAlert() {
//		Alerter.create(this)
//				.setIcon(R.drawable.ic_baseline_error_24_white)
//				.setTitle(getString(R.string.error_content))
//				.setText(R.string.error_desc)
//				.setBackgroundColor(R.color.gray_666666)
//				.show()
//}
//
//fun Activity.showNetworkAlert() {
//		Alerter.create(this)
//				.setIcon(R.drawable.ic_baseline_network_wifi_24_white)
//				.setTitle(getString(R.string.network_content))
//				.setText(R.string.network_desc)
//				.setBackgroundColor(R.color.red_e50014)
//				.show()
//}
//
//fun Activity.showGoogleServiceAlert() {
//		Alerter.create(this)
//				.setIcon(R.drawable.ic_google_play)
//				.setTitle(getString(R.string.google_play_service_title))
//				.setText(R.string.google_play_service_text)
//				.setBackgroundColor(R.color.yellow_f7cd3b)
//				.show()
//}
//
