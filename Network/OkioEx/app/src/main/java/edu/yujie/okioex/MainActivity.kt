package edu.yujie.okioex

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import com.tbruyelle.rxpermissions3.RxPermissions
import edu.yujie.okioex.databinding.ActivityMainBinding
import okio.buffer
import okio.sink
import okio.source
import java.io.File

class MainActivity : AppCompatActivity() {
    private val fileName = "doc.txt"

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val view = findViewById<View>(android.R.id.content)
        val rxPermissions = RxPermissions(this)

        binding.btnWrite.clicks().compose(rxPermissions.ensure(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)).subscribe {
            val file = File(Environment.getStorageDirectory(), fileName)
            if (!file.exists()) {
                file.mkdirs()
                file.sink().buffer().writeUtf8(content)
                Snackbar.make(view, "Write Success", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(view, "File Exists", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btnRead.clicks().compose(rxPermissions.ensure(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)).subscribe {

        }


//        val inputStream = assets.open("doc.txt")
//        try {
//            val bufferSource = inputStream.source().buffer().use {
//                lifecycleScope.launch(Dispatchers.IO) {
//                    val content = it.readUtf8Line()
//                    println(content)
//                    delay(100)
//                }
//            }
//        } catch (e: IOException) {
//
//        } finally {
//
//        }

    }

    fun readLines(file: File) {
        val fileSource = file.source()
        val bufferedSource = fileSource.buffer()
        val buffer = bufferedSource.buffer
        while (true) {
            val line =
                buffer.readUtf8Line()
                    ?: break
            println(line)
        }
    }
}