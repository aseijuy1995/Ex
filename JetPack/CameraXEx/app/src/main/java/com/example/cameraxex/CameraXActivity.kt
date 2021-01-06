package com.example.cameraxex

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.cameraxex.databinding.ActivityCameraxBinding
import com.jakewharton.rxbinding4.view.clicks
import com.tbruyelle.rxpermissions3.RxPermissions
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

const val REQUEST_CODE_CAMERA = 1001

class CameraXActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private lateinit var binding: ActivityCameraxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityCameraxBinding>(this, R.layout.activity_camerax)

        binding.btnCapture.clicks()
            .compose(
                RxPermissions(this).ensure(
                    Manifest.permission.CAMERA
                )
            ).subscribe {
                startActivityForResult(Intent(this, CameraActivity::class.java), REQUEST_CODE_CAMERA)
            }.addTo(compositeDisposable)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}