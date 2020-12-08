package edu.yujie.rxpermissionex

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tbruyelle.rxpermissions3.RxPermissions


class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvView = findViewById<TextView>(R.id.tv_view)
        val rxPermission = RxPermissions(this)

        tvView.setOnClickListener {
//            //permission, granted true || false
//            rxPermission.request(Manifest.permission.CAMERA)
//                .subscribe { granted ->
//                    if (granted) {
//                        Toast.makeText(this, "Allow", Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(this, "Deny", Toast.LENGTH_SHORT).show()
//                    }
//                }

            rxPermission.requestEach(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                .subscribe {
                    println("$TAG onNext() it = $it")
                    if (it.granted) {
                        Toast.makeText(this, "Allow", Toast.LENGTH_SHORT).show()
                    } else if (it.shouldShowRequestPermissionRationale) {
                        Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", packageName, null)
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
        }


    }
}