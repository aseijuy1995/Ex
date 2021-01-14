package edu.yujie.socketex.util

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

fun Context.getContentUri(file: File): Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
    FileProvider.getUriForFile(this, "${packageName}.fileProvider", file)
else
    Uri.fromFile(file)