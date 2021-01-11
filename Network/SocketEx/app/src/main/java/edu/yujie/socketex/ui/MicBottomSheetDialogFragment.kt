package edu.yujie.socketex.ui

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import edu.yujie.socketex.ChatRoomViewModel
import edu.yujie.socketex.R
import edu.yujie.socketex.databinding.FragmentMicBottomSheetDialogBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MicBottomSheetDialogFragment : BaseBottomSheetDialogFragment<FragmentMicBottomSheetDialogBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_mic_bottom_sheet_dialog

    private val viewModel by sharedViewModel<ChatRoomViewModel>()

    private var fileName: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.ivRecorder.longClicks { true }.subscribe {
////            val fileName = "Audio_${System.nanoTime()}.3gp"
////            viewModel.starRecorder(fileName)
//        }

        binding.ivRecorder.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    fileName = "Audio_${System.nanoTime()}.3gp"
                    viewModel.startRecorder(fileName!!)
                }
                MotionEvent.ACTION_UP -> {
                    viewModel.stopRecorder {
                        Snackbar.make(binding.ivRecorder, "Less than 1 second", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
                    }
                }
            }
            true
        }

        viewModel.recorderStateLiveData.observe(viewLifecycleOwner) {
            binding.ivRecorder.setImageResource(if (it) R.drawable.ic_baseline_fiber_manual_record_24_red else R.drawable.ic_baseline_fiber_manual_record_24_blue)
        }
    }
}