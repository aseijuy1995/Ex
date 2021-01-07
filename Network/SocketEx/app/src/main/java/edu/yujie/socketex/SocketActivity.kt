package edu.yujie.socketex

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.view.longClicks
import com.tbruyelle.rxpermissions3.RxPermissions
import edu.yujie.socketex.databinding.ActivitySocketBinding
import edu.yujie.socketex.util.closeKeyBoard
import edu.yujie.socketex.util.isVisible
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.WebSocket
import org.koin.androidx.viewmodel.ext.android.viewModel


//https://juejin.cn/post/6844903461796970504
//https://notfalse.net/7/three-way-handshake
//https://www.jianshu.com/p/089fb79e308b
//https://www.jianshu.com/p/8175f51e662c
//https://blog.csdn.net/xlh1191860939/article/details/103216735

//https://www.jianshu.com/p/45d27f3e1196
//https://www.jianshu.com/p/a6d086a3997d

class SocketActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private lateinit var binding: ActivitySocketBinding
    private val viewModel by viewModel<SocketViewModel>()
    private val infoListAdapter = InfoListAdapter()
    private val chatListAdapter = ChatListAdapter()
    private val compositeDisposable = CompositeDisposable()
    private val requestCode = 1001
    private lateinit var webSocketClient: WebSocket
    private lateinit var rxPermission: RxPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivitySocketBinding>(this, R.layout.activity_socket)
        initView()
        //
        lifecycleScope.launch {
            viewModel.startMockServer().collect {
                when (it) {
                    is SocketState.onServerOpen -> {
                        Snackbar.make(binding.rvInfo, "Server onOpen()", Snackbar.LENGTH_SHORT).setAnchorView(binding.itemFeatureBar.root).show()

                        viewModel.addInfo(it.msg)
                    }
                    is SocketState.onServerMessage -> viewModel.addInfo(it.msg)
                    is SocketState.onServerClosing -> viewModel.addInfo(it.msg)
                    is SocketState.onServerClosed -> viewModel.addInfo(it.msg)
                    is SocketState.onServerFailure -> {
                        Snackbar.make(binding.rvInfo, "Server onFailure()", Snackbar.LENGTH_SHORT).show()
                        viewModel.addInfo(it.msg)
                    }

                    is SocketState.ShowEmptyText -> Snackbar.make(binding.itemFeatureBar.ivSend, it.msg, Snackbar.LENGTH_SHORT).setAnchorView(binding.itemFeatureBar.root).show()
                    is SocketState.ShowMsg -> {
                        binding.itemFeatureBar.etText.setText("")
                        viewModel.addChat(it.chatBean)
                    }

                    is SocketState.onClientOpen -> {
//                        Snackbar.make(binding.rvInfo, "Client onOpen()", Snackbar.LENGTH_SHORT).show()
                        viewModel.addInfo(it.msg)
                    }
                    is SocketState.onClientMessage -> {
                        viewModel.addInfo(it.msg)
                        viewModel.addChat(it.chatBean)
                    }
                    is SocketState.onClientClosing -> viewModel.addInfo(it.msg)
                    is SocketState.onClientClosed -> viewModel.addInfo(it.msg)
                    is SocketState.onClientFailure -> {
                        Snackbar.make(binding.rvInfo, "Client onFailure()", Snackbar.LENGTH_SHORT).show()
                        viewModel.addInfo(it.msg)
                    }
                    is SocketState.RecordState -> binding.itemFeatureBar.ivMic.isVisible(it.state)

                    is SocketState.RecordAudioData -> {
                        println("RecordAudioData:RecordAudioData")
                    }
                }
            }
        }
        viewModel.urlLiveData.observe(this) {
            webSocketClient = viewModel.startWebSocket(it)
        }
        viewModel.infoListLiveData.observe(this) {
            println("infoList:$it")
            refreshInfo(it)
        }
        viewModel.chatListLiveData.observe(this) {
            println("chatList:$it")
            refreshChat(it)
        }

        binding.itemFeatureBar.ivCamera.clicks().compose(rxPermission.ensure(Manifest.permission.CAMERA)).subscribe {

        }.addTo(compositeDisposable)

        binding.itemFeatureBar.ivPhoto.clicks().compose(rxPermission.ensure(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)).subscribe {
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), requestCode)
        }.addTo(compositeDisposable)

        binding.itemFeatureBar.ivRecord.clicks().subscribe {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.socketViewEvent.send(SocketViewEvent.RecordClick)
            }
        }.addTo(compositeDisposable)

        binding.itemFeatureBar.ivMic.longClicks { true }.compose(
            rxPermission.ensure(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ).subscribe {
            binding.itemFeatureBar.ivMic.setImageResource(R.drawable.ic_baseline_mic_none_24_red)
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.socketViewEvent.send(SocketViewEvent.ReCordStart(contentResolver))
                println("SocketViewEvent.ReCordStart")
            }
        }

//        binding.itemFeatureBar.ivMic.touches { true }.subscribe {
//            if (it.action == MotionEvent.ACTION_UP) {
//                binding.itemFeatureBar.ivMic.setImageResource(R.drawable.ic_baseline_mic_none_24_gray)
//                lifecycleScope.launch(Dispatchers.IO) {
//                    viewModel.socketViewEvent.send(SocketViewEvent.ReCordStop)
//                }
//            }
//        }

        binding.itemFeatureBar.ivSend.clicks().subscribe {
            val text = binding.itemFeatureBar.etText.text.toString().trim()
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.socketViewEvent.send(SocketViewEvent.SendClick(text))
            }
        }.addTo(compositeDisposable)

        binding.rvView.clicks().subscribe {
            closeKeyBoard(binding.rvView)
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.socketViewEvent.send(SocketViewEvent.RecordClick)
            }
        }.addTo(compositeDisposable)
    }

    private fun initView() {
        binding.rvInfo.adapter = infoListAdapter
        binding.rvView.adapter = chatListAdapter
        rxPermission = RxPermissions(this)
    }

    private fun refreshInfo(infoList: List<String>) {
        infoListAdapter.submitList(infoList)

        binding.rvInfo.scrollToPosition(infoList.size)
        binding.rvInfo.postDelayed({
            binding.rvInfo.smoothScrollToPosition(infoList.size)
        }, 50)
    }

    private fun refreshChat(chatList: List<ChatBean>) {
        closeKeyBoard(binding.itemFeatureBar.ivSend)
        chatListAdapter.submitList(chatList)

        binding.rvView.scrollToPosition(chatList.size)
        binding.rvView.postDelayed({
            binding.rvView.smoothScrollToPosition(chatList.size)
        }, 50)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            this.requestCode -> {
                if (resultCode == Activity.RESULT_OK) {
                    val url = data?.data!!
                    val byteArray = contentResolver.openInputStream(url)?.buffered().use { it?.readBytes() }
                    val chatBean = viewModel.convertByteBean(byteArray)
                    val json = Gson().toJson(chatBean)
                    println("json:$json")
                    webSocketClient.send(json)
                    viewModel.addChat(chatBean)

//                    //bitmap
//                    val bitmap = contentResolver.openInputStream(url)?.buffered().use {
//                        BitmapFactory.decodeStream(it)
//                    }
//                    binding.ivImg.setImageBitmap(bitmap)
                }
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}