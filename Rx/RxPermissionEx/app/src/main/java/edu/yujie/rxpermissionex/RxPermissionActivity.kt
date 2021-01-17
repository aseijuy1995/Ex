package edu.yujie.rxpermissionex

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import com.tbruyelle.rxpermissions3.RxPermissions
import edu.yujie.rxpermissionex.databinding.ActivityRxPermissionBinding

//https://github.com/tbruyelle/RxPermissions
//https://blog.csdn.net/yeluofengchui/article/details/91126163

class RxPermissionActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    private lateinit var binding: ActivityRxPermissionBinding

    private lateinit var rxPermission: RxPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityRxPermissionBinding>(this, R.layout.activity_rx_permission)
        rxPermission = RxPermissions(this)

//        binding.tvView.setOnClickListener {
////            // Return granted
////            rxPermission.request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
////                .subscribe { granted ->
////                    println("$TAG granted = $granted")
////                    val str = if (granted) "Allow" else "Deny"
////                    Snackbar.make(it, str, Snackbar.LENGTH_SHORT).show()
////                }
//
//            //--------------------------------------------------------------------------------
//
////            // Return permission
////            rxPermission.requestEach(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
////                .subscribe { permission ->
////                    var str: String = ""
////                    println("$TAG granted = ${permission.granted}, name = ${permission.name}, rationale = ${permission.shouldShowRequestPermissionRationale}")
////                    when (permission.name) {
////                        Manifest.permission.CAMERA -> {
////                            str += "name = ${permission.name}, granted = ${permission.granted}, rationale = ${permission.shouldShowRequestPermissionRationale}\n"
////                        }
////                        Manifest.permission.RECORD_AUDIO -> {
////                            str += "name = ${permission.name}, granted = ${permission.granted}, rationale = ${permission.shouldShowRequestPermissionRationale}\n"
////                        }
////                    }
////                    Snackbar.make(it, str, Snackbar.LENGTH_SHORT).show()
////                }
//
//            //--------------------------------------------------------------------------------
//
////            // Go Setting Page
////            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))
////            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
////            startActivity(intent)
//
//            //--------------------------------------------------------------------------------
//
////            rxPermission.requestEachCombined(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
////                .subscribe { permission ->
////                    println("$TAG name = ${permission.name}, granted = ${permission.granted}, shouldShowRequestPermissionRationale = ${permission.shouldShowRequestPermissionRationale}")
////                    val str = if (permission.granted) "Allow" else "Deny"
////                    Snackbar.make(it, str, Snackbar.LENGTH_SHORT).show()
////                }
//
//        }

        //--------------------------------------------------------------------------------

        binding.tvView.clicks()
            .compose(rxPermission.ensure(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO))
            .subscribe {
                val str = if (it) "Allow" else "Deny"
                Snackbar.make(binding.tvView, str, Snackbar.LENGTH_SHORT).show()
            }

    }
}
