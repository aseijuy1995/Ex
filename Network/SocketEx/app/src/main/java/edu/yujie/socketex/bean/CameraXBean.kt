package edu.yujie.socketex.bean

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview

data class CameraXSetting(
    val preview: Preview,
    val imageCapture: ImageCapture,
    val cameraSelector: CameraSelector
)