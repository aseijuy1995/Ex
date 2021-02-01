package edu.yujie.socketex.util

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make
import edu.yujie.socketex.R

class SnackbarUtil {

    companion object {
        fun makePermissionDeny(context: Context, view: View): Snackbar =
            make(view, context.resources.getString(R.string.permission_deny), Snackbar.LENGTH_LONG)
//                .setAction(context.resources.getString(R.string.go_settings))
    }

}


