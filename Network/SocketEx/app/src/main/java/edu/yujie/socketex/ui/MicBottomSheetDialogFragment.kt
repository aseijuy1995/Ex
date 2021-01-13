package edu.yujie.socketex.ui

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.touches
import edu.yujie.socketex.R
import edu.yujie.socketex.SocketViewEvent
import edu.yujie.socketex.base.BaseBottomSheetDialogFragment
import edu.yujie.socketex.databinding.FragmentMicBottomSheetDialogBinding
import edu.yujie.socketex.vm.ChatRoomViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.concurrent.TimeUnit

class MicBottomSheetDialogFragment : BaseBottomSheetDialogFragment<FragmentMicBottomSheetDialogBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_mic_bottom_sheet_dialog

    private val viewModel by sharedViewModel<ChatRoomViewModel>()

    private var disposable: Disposable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivRecorder.touches { true }.subscribe {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    viewModel.startRecorder()
                }
                MotionEvent.ACTION_UP -> {
                    viewModel.stopRecorder {
                        Snackbar.make(binding.ivRecorder, "Less than 1 second", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
                    }
                }
            }
        }

        viewModel.recorderStateFileLiveData.observe(viewLifecycleOwner) {
            if (it.first) {
                binding.ivRecorder.setImageResource(R.drawable.ic_baseline_fiber_manual_record_24_red)
                disposable = Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        val sf = String.format("%ss", it.toString())
                        binding.tvTime.apply {
                            visibility = View.VISIBLE
                            text = sf
                        }
                    }.addTo(compositeDisposable)
            } else {
                binding.ivRecorder.setImageResource(R.drawable.ic_baseline_fiber_manual_record_24_blue)
                it.second?.let {
                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.socketViewEvent.send(SocketViewEvent.SendRecorder(it))
                    }
                }
                binding.tvTime.visibility = View.INVISIBLE
                disposable?.dispose()
            }
        }
    }
}