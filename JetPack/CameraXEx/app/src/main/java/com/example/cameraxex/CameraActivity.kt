package com.example.cameraxex

import android.os.Bundle
import android.util.Log
import android.util.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.cameraxex.databinding.ActivityCameraBinding
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class CameraActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private val compositeDisposable = CompositeDisposable()
    private lateinit var binding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityCameraBinding>(this, R.layout.activity_camera)
        startCamera()

        binding.ivSlap.clicks()
            .subscribe {

            }.addTo(compositeDisposable)
    }

    private fun startCamera() {
        val listenableFuture = ProcessCameraProvider.getInstance(this)

        listenableFuture.addListener(
            {
                val cameraProvider = listenableFuture.get()
                bindPreview(cameraProvider)


                val preview = Preview.Builder().build().apply {
                    setSurfaceProvider(binding.previewView.surfaceProvider)
                }

                val cameraSelector = CameraSelector.Builder().apply {
                    requireLensFacing(CameraSelector.LENS_FACING_BACK)
                }.build()

                val imageCache = ImageCapture.Builder().apply {
                    setFlashMode(ImageCapture.FLASH_MODE_AUTO)
//                    setTargetAspectRatio(Surface.ROTATION_270)
                }.build()


                val imageAnalysis = ImageAnalysis.Builder().apply {
                    setTargetResolution(Size(1280, 700))
                }.build()

                //
                cameraProvider

                try {
//                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCache)
                } catch (e: Exception) {
                    Log.e(TAG, "Use case binding failed")
                }
            },
            ContextCompat.getMainExecutor(this)
        )

    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider?) {
        Preview.Builder().build()

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}