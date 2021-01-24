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
//
//        viewModel.stateRelay.subscribeWithLife {
//            println("$TAG stateRelay:　$it")
//            if (it) {
//                binding.ivCircle.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24_red)
//                binding.tvTime.visibility = View.VISIBLE
//            } else {
//                binding.ivCircle.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24_gray)
//                binding.tvTime.visibility = View.INVISIBLE
//            }
//        }

//        viewModel.recordingTimeRelay.subscribeWithLife {
//            println("$TAG recordingTimeRelay: $it")
//
//        }

        viewModel.lessTimeRelay.subscribeWithLife {
            println("$TAG lessTimeRelay:$it")
            if (it) {
                Snackbar.make(binding.ivRecorder, "Less than 1 second", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
            }
        }
//
//        viewModel.recorderStateRelay.subscribe {
//            if (it) {
//                Snackbar.make(binding.ivRecorder, "Less than 1 second", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
//            }
//        }

//        viewModel.recorderStateFileLiveData.observe(viewLifecycleOwner) {
//            if (it.first) {
//                binding.ivRecorder.setImageResource(R.drawable.ic_baseline_fiber_manual_record_24_red)
//                disposable = Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe {
//                        val sf = String.format("%ss", it.toString())
//                        binding.tvTime.apply {
//                            visibility = View.VISIBLE
//                            text = sf
//                        }
//                    }.addTo(compositeDisposable)
//            } else {
//                binding.ivRecorder.setImageResource(R.drawable.ic_baseline_fiber_manual_record_24_blue)
//                it.second?.let {
//                    lifecycleScope.launch(Dispatchers.IO) {
//                        viewModel.socketViewEvent.send(SocketViewEvent.SendRecorder(it))
//                    }
//                }
//                binding.tvTime.visibility = View.INVISIBLE
//                disposable?.dispose()
//            }
//        }
    }

    private fun initView() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@RecordingDialogFragment.viewModel
        }
    }
}