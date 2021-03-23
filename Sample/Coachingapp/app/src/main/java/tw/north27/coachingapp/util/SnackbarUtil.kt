package tw.north27.coachingapp.util

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import com.google.android.material.snackbar.Snackbar
import tw.north27.coachingapp.R


object SnackbarUtil {

    fun showPermissionDeny(view: View) =
        Snackbar.make(view, view.context.resources.getString(R.string.permission_deny), Snackbar.LENGTH_LONG)
            .setAction(view.context.resources.getString(R.string.go_settings)) {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.data = Uri.parse("package:" + view.context.packageName)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                view.context.startActivity(intent)
            }
            .show()

}