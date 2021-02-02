package edu.yujie.socketex.finish.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.camera.core.CameraXConfig
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import edu.yujie.socketex.databinding.FragmentCameraXBinding
import edu.yujie.socketex.finish.base.fragment.BaseViewBindingFragment
import edu.yujie.socketex.util.CameraXUtil
import java.io.File

class CameraXFragment : BaseViewBindingFragment<FragmentCameraXBinding>(FragmentCameraXBinding::inflate) {

    private var imageCapture: ImageCapture? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startCamera()
        binding.ivBtnShutter.clicks().subscribeWithLife { takeCapture() }
    }

    private fun startCamera() {
        val cameraXUtil = CameraXUtil.Builder(requireContext()).apply {
            buildPreview()
            buildImageCapture()
            buildCameraSelector()
        }.build()
        cameraXUtil.startCamera(viewLifecycleOwner, binding.preview)
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