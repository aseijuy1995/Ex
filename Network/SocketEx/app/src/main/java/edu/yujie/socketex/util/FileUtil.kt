package edu.yujie.socketex.util

import io.reactivex.rxjava3.subjects.BehaviorSubject
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

fun File.createFile(): BehaviorSubject<File> {
    val subject = BehaviorSubject.create<File>()
    if (exists()) delete()
    createNewFile()
    subject.onNext(this)
    return subject
}