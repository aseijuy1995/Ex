package edu.yujie.socketex.util

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import edu.yujie.socketex.bean.CameraXSetting

class CameraXUtil(private val context: Context) {
    private val TAG = javaClass.simpleName

    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector? = null

    fun buildPreview(preview: Preview? = null): CameraXUtil {
        this.preview = preview ?: Preview.Builder()
            .setTargetName(TAG)
//            .setTargetAspectRatio(aspectRatio)
//            .setTargetResolution(resolution)
            .build()
        return this
    }

    fun buildImageCapture(imageCapture: ImageCapture? = null): CameraXUtil {
        this.imageCapture = imageCapture ?: ImageCapture.Builder()
            .setTargetName(TAG)
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
        return this
    }

    fun buildCameraSelector(cameraSelector: CameraSelector? = null): CameraXUtil {
        this.cameraSelector = cameraSelector ?: CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        return this
    }

    fun startCamera(setting: CameraXSetting) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
//                    it.setSurfaceProvider(viewFinder.createSurfaceProvider())
            }
            imageCapture = ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build()
//                val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                val camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            preview.setSurfaceProvider(preview.surfaceProvider)
        }, ContextCompat.getMainExecutor(context))
    }


}