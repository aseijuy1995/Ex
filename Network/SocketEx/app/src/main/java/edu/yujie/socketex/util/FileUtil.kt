package edu.yujie.socketex.util

import java.io.File

object FileUtil {

    fun createFile(filePath: File?, fileName: String): File {
        val file = File(filePath, fileName)
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        return file
    }

}