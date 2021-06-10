package tw.north27.coachingapp.ext

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.updateApp(versionName: String) = UpdateApp.with(this, versionName)

fun Activity.updateApp(versionCode: Long) = UpdateApp.with(this, versionCode)

fun Activity.hideKeyBoard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view: View? = currentFocus
    if (view == null)
        view = View(this)
    imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}