package edu.yujie.viewmodelex

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private val viewModel by viewModels<MyViewModel> { MyViewModelFactory }
    private val savedStateViewModel: SavedStateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.users.observe(this) {
            val msg = "users = $it"
            findViewById<TextView>(R.id.tvView).text = msg
        }

        //SavedStateHandle
        findViewById<Button>(R.id.btnGetSave).setOnClickListener {
            val msg = savedStateViewModel.getUserState()
            println("$TAG:$msg")
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 按下Home鍵 : Activity 進入了後臺, 此時會呼叫該方法
     * 按下電源鍵 : 螢幕關閉, Activity 進入後臺
     * 啟動其它 Activity : Activity 被壓入了任務棧的棧底
     * 橫豎屏切換 : 會銷燬當前 Activity 並重新建立
     * */
    override fun onSaveInstanceState(outState: Bundle) {
        savedStateViewModel.setUserState()
        super.onSaveInstanceState(outState)
    }
}