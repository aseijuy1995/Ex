package edu.yujie.socketex.ui

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.touches
import edu.yujie.socketex.R
import edu.yujie.socketex.base.BaseBottomSheetDialogFragment
import edu.yujie.socketex.bean.RecorderSetting
import edu.yujie.socketex.databinding.FragmentRecordingDialogBinding
import edu.yujie.socketex.vm.ChatRoomViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RecordingDialogFragment : BaseBottomSheetDialogFragment<FragmentRecordingDialogBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_recording_dialog

    private val viewModel by sharedViewModel<ChatRoomViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        binding.ivRecorder.touches { true }.subscribe {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    viewModel.startRecording(setting = RecorderSetting(context = requireContext()))
                }

                MotionEvent.ACTION_UP -> {
                    viewModel.stopRecording()
                }
            }
        }

        viewModel.recordingDoneRelay.subscribeWithLife { (isDone, file) ->
            if (isDone) {
                if (file == null) {
                    Snackbar.make(binding.ivRecorder, "Can not find Recorder file path", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
                }
            } else
                Snackbar.make(binding.ivRecorder, "Less than 1 second", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
        }

    }

    private fun initView() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@RecordingDialogFragment.viewModel
        }
    }
}