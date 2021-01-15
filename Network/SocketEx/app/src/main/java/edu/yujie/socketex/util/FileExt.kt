package edu.yujie.socketex.util

import java.io.File

fun File.createFile(): File {
    if (exists()) delete()
    createNewFile()
    return this
}

object FileExt {
    fun createFile(filePath: File?, fileName: String): File {
        val file = File(filePath, fileName)
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        return file
    }
}

//fun File.createFile(): BehaviorSubject<File> {
//    val subject = BehaviorSubject.create<File>()
//    if (exists()) delete()
//    createNewFile()
//    subject.onNext(this)
//    return subject
//}

