package edu.yujie.fileex

import android.Manifest
import android.os.Bundle
import android.os.Environment
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.piasy.rxandroidaudio.StreamAudioRecorder
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.view.touches
import com.tbruyelle.rxpermissions3.RxPermissions
import edu.yujie.fileex.databinding.ActivityFileBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileActivity : AppCompatActivity() {
    private lateinit var rxPermission: RxPermissions
    private lateinit var binding: ActivityFileBinding
    private val audioRecordUtil = AudioRecordUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityFileBinding>(this, R.layout.activity_file)
        rxPermission = RxPermissions(this)

        binding.ivMic.clicks().compose(
            rxPermission.ensure(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ).subscribe {
            if (it)
                touchClick(binding, it)
            else
                Snackbar.make(binding.ivMic, "Permission Deiend!", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun touchClick(binding: ActivityFileBinding, it: Boolean) {
        binding.ivMic.touches { true }.subscribe {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.ivMic.setImageResource(R.drawable.ic_baseline_mic_none_24_red)
//                    startRecording()
                    audioRecordUtil.doStart()
                    Snackbar.make(binding.ivMic, "ACTION_DOWN", Snackbar.LENGTH_SHORT).show()
                }
                MotionEvent.ACTION_UP -> {
                    binding.ivMic.setImageResource(R.drawable.ic_baseline_mic_none_24_gray)
//                    stopRecording()
                    audioRecordUtil.doStop()
                    Snackbar.make(binding.ivMic, "ACTION_UP", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }


    var mOutputFile: File? = null
    var mStreamAudioRecorder: StreamAudioRecorder? = null

    private fun startRecording() {
        mOutputFile = File(Environment.getExternalStorageDirectory().absolutePath + File.separator + System.nanoTime() + ".stream.m4a");
        mOutputFile?.createNewFile();
        val mFileOutputStream = FileOutputStream(mOutputFile);
        val mStreamAudioRecorder = StreamAudioRecorder.getInstance()
        mStreamAudioRecorder.start(object : StreamAudioRecorder.AudioDataCallback {
            override fun onAudioData(data: ByteArray?, size: Int) {
                try {
                    println("data:$data, size:size, file:$mOutputFile")
                    mFileOutputStream.write(data, 0, size);
                } catch (e: IOException) {
                    e.printStackTrace();
                }
            }

            override fun onError() {
                binding.ivMic.post {
                    Snackbar.make(binding.ivMic, "onError", Snackbar.LENGTH_SHORT).show()
                    binding.ivMic.setImageResource(R.drawable.ic_baseline_mic_none_24_gray)
                }
            }
        })
    }

    private fun stopRecording() {
        mStreamAudioRecorder?.stop()
    }
}