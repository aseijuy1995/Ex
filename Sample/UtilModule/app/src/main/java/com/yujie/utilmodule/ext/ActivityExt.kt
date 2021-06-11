package com.yujie.utilmodule.ext

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.tapadoo.alerter.Alerter
import com.yujie.utilmodule.R

/**
 * 錯誤警報
 * */
fun Activity.alertError(
		@DrawableRes drawableRes: Int = R.drawable.ic_error,
		title: String = getString(R.string.err_title),
		text: String = getString(R.string.err_text),
		@ColorRes backColorRes: Int = R.color.yellow_f7cd3b,
		duration: Long = 5000
) {
		Alerter.create(this)
				.setIcon(drawableRes)
				.setIconColorFilter(0)
				.setTitle(title)
				.setText(text)
				.setBackgroundColorRes(backColorRes)
				.setDuration(duration)
				.show()
}

/**
 * 無網路警報
 * */
fun Activity.alertNetwork(
		@DrawableRes drawableRes: Int = R.drawable.ic_network,
		title: String = getString(R.string.net_title),
		text: String = getString(R.string.net_text),
		@ColorRes backColorRes: Int = R.color.orange_f09401,
		duration: Long = 5000
) {
		Alerter.create(this)
				.setIcon(drawableRes)
				.setIconColorFilter(0)
				.setTitle(title)
				.setText(text)
				.setBackgroundColorRes(backColorRes)
				.setDuration(duration)
				.show()
}

/**
 * Google Service警報
 * */
fun Activity.alertGoogleService(
		@DrawableRes drawableRes: Int = R.drawable.ic_google_play,
		title: String = getString(R.string.play_service_title),
		text: String = getString(R.string.play_service_text),
		@ColorRes backColorRes: Int = R.color.blue_02abe2,
		duration: Long = 5000
) {
		Alerter.create(this)
				.setIcon(drawableRes)
				.setIconColorFilter(0)
				.setTitle(title)
				.setText(text)
				.setBackgroundColorRes(backColorRes)
				.setDuration(duration)
				.show()
}

/**
 * 收起鍵盤
 * */
fun Activity.hideKeyBoard() {
		val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
		var view: View? = currentFocus
		if (view == null)
				view = View(this)
		imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}
