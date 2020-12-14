package edu.yujie.socketex

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import edu.yujie.retrofitex.OkHttpUtil
import edu.yujie.retrofitex.webSocket
import edu.yujie.socketex.databinding.ActivitySocketBinding
import okhttp3.WebSocket

//https://www.jianshu.com/p/45d27f3e1196
//https://www.jianshu.com/p/a6d086a3997d

class SocketActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private var webSocket: WebSocket? = null
    private val url = "ws://echo.websocket.org"
    private val adapter = InfoListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySocketBinding>(this, R.layout.activity_socket)

        binding.rvInfo.apply {
            layoutManager = LinearLayoutManager(this@SocketActivity, LinearLayoutManager.VERTICAL, false)
            adapter = this@SocketActivity.adapter
        }

        binding.btnStart.setOnClickListener {

        }

        binding.btnView.setOnClickListener {
            val text = binding.etText.text.toString().trim()
            if (TextUtils.isEmpty(text)) {
                Snackbar.make(it, "Can not empty!", Snackbar.LENGTH_SHORT).show()
            } else {
                webSocket?.send(text)
            }
        }


        chatsLiveData.observe(context, Observer{

        })
    }
}