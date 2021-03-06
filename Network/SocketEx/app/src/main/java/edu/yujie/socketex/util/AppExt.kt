package edu.yujie.socketex.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


fun Context.closeKeyBoard(vararg views: View) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    views.map {
        it.windowToken
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}