package edu.yujie.rxpermissionex

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tbruyelle.rxpermissions3.RxPermissions

//https://github.com/tbruyelle/RxPermissions
//https://blog.csdn.net/yeluofengchui/article/details/91126163

class RxPermissionActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvView = findViewById<TextView>(R.id.tv_view)
        val rxPermission = RxPermissions(this)

        tvView.setOnClickListener {
//            // Return granted
//            rxPermission.request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
//                .subscribe { granted ->
//                    println("$TAG granted = $granted")
//                    val str = if (granted) "Allow" else "Deny"
//                    Snackbar.make(it, str, Snackbar.LENGTH_SHORT).show()
//                }

//            // Return permission
//            var str: String = ""
//            rxPermission.requestEach(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
//                .subscribe { permission ->
//                    println("$TAG granted = ${permission.granted}, name = ${permission.name}, shouldShowRequestPermissionRationale = ${permission.shouldShowRequestPermissionRationale}")
//                    when (permission.name) {
//                        Manifest.permission.CAMERA -> {
//                            str += "name = ${permission.name}, granted = ${permission.granted}, shouldShowRequestPermissionRationale = ${permission.shouldShowRequestPermissionRationale}\n"
//                        }
//                        Manifest.permission.RECORD_AUDIO -> {
//                            str += "name = ${permission.name}, granted = ${permission.granted}, shouldShowRequestPermissionRationale = ${permission.shouldShowRequestPermissionRationale}\n"
//                        }
//                    }
//                    Snackbar.make(it, str, Snackbar.LENGTH_SHORT).show()
//                }

//            // Go Setting Page
//            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)

//            rxPermission.requestEachCombined(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
//                .subscribe { permission ->
//                    println("$TAG name = ${permission.name}, granted = ${permission.granted}, shouldShowRequestPermissionRationale = ${permission.shouldShowRequestPermissionRationale}")
//                    val str = if (permission.granted) "Allow" else "Deny"
//                    Snackbar.make(it, str, Snackbar.LENGTH_SHORT).show()
//                }

        }

    }
}
