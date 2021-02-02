package edu.yujie.socketex.util

import android.content.Context
import android.util.Size
import android.view.OrientationEventListener
import android.view.Surface
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

class CameraXUtil private constructor(
    private val context: Context,
    private val preview: Preview?,
    private val imageCapture: ImageCapture?,
    private val cameraSelector: CameraSelector?
) {
    private val TAG = javaClass.simpleName

    class Builder(private val context: Context) {
        private val TAG = javaClass.simpleName
        private var preview: Preview? = null
        private var imageCapture: ImageCapture? = null
        private var cameraSelector: CameraSelector? = null
        private var imageAnalysis: ImageAnalysis? = null

        fun buildPreview(preview: Preview? = null): Builder {
            this.preview = preview ?: Preview.Builder()
                .setTargetName(TAG)
//            .setTargetAspectRatio(aspectRatio)
//            .setTargetResolution(resolution)
                .build()
            return this
        }

        fun buildImageCapture(imageCapture: ImageCapture? = null): Builder {
            this.imageCapture = imageCapture ?: ImageCapture.Builder()
                .setTargetName(TAG)
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()
            return this
        }

        fun setRotation(): Builder {
            val orientationEventListener = object : OrientationEventListener(context) {
                override fun onOrientationChanged(orientation: Int) {
                    imageCapture?.targetRotation = when (orientation) {
                        in 45..134 -> Surface.ROTATION_270
                        in 135..224 -> Surface.ROTATION_180
                        in 225..314 -> Surface.ROTATION_90
                        else -> Surface.ROTATION_0
                    }
                }
            }
            orientationEventListener.enable()
            return this
        }


        fun buildCameraSelector(cameraSelector: CameraSelector? = null): Builder {
            this.cameraSelector = cameraSelector ?: CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            return this
        }

        fun buildImageAnalysis() {
            this.imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(1280, 700))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
        }

        fun build(): CameraXUtil = CameraXUtil(context, preview, imageCapture, cameraSelector)
    }


    fun startCamera(owner: LifecycleOwner, previewView: PreviewView) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()

            try {
                cameraProvider.unbindAll()
                val camera = cameraProvider.bindToLifecycle(owner, cameraSelector!!, preview, imageCapture)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            preview!!.setSurfaceProvider(previewView.surfaceProvider)
        }, ContextCompat.getMainExecutor(context))
    }


}