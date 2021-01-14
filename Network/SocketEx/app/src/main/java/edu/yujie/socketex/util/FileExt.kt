package edu.yujie.socketex.util

import com.jakewharton.rxrelay3.BehaviorRelay
import java.io.File

fun File.createFile(): BehaviorRelay<File> {
    val behaviorRelay = BehaviorRelay.create<File>()
    if (exists()) delete()
    createNewFile()
    behaviorRelay.accept(this)
    return behaviorRelay
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

