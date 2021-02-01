package edu.yujie.socketex.finish.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import edu.yujie.socketex.databinding.FragmentCameraXBinding
import edu.yujie.socketex.finish.base.fragment.BaseViewBindingFragment
import java.io.File

class CameraXFragment : BaseViewBindingFragment<FragmentCameraXBinding>(FragmentCameraXBinding::inflate) {

    private var imageCapture: ImageCapture? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startCamera()
        binding.ivBtnShutter.clicks().subscribeWithLife { takeCapture() }

    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
//            val preview = Preview.Builder().build().also {
////                    it.setSurfaceProvider(viewFinder.createSurfaceProvider())
//            }
//            imageCapture = ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build()
//                val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build()
//            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                val camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            preview.setSurfaceProvider(binding.preview.surfaceProvider)
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takeCapture() {
        val imageCapture = imageCapture ?: return

        val fileName: String = "Image_${System.nanoTime()}.jpg"
        val file = File(requireContext().externalCacheDir, fileName)

        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(requireContext()), object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val uri = Uri.fromFile(file)
                println("$TAG uri = $uri")
            }

            override fun onError(e: ImageCaptureException) {
                Snackbar.make(binding.root, e.message ?: "", Snackbar.LENGTH_SHORT).show()
            }

        })


    }
}