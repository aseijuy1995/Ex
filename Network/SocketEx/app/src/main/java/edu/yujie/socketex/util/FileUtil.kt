package edu.yujie.socketex.util

import android.content.Context
import java.io.File

class FileUtil(private val context: Context) {

    companion object : SingletonProperty<FileUtil, Context>(::FileUtil)


    fun getCacheFile(fileName: String): File {
        val file = File(context.externalCacheDir, fileName)
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        return file


//        val imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//            FileProvider.getUriForFile(context, "${context.packageName}.fileProvider", file)
//        else
//            Uri.fromFile(file)
//        return imageUri
    }
}