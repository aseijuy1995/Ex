package tw.north27.coachingapp.ext

import java.io.File

fun File.createFile(fileName: String): File {

    val file = File(this, fileName)

    if (file.exists()) file.delete()

    file.createNewFile()

    return file
}