package edu.yujie.livedataex

import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import edu.yujie.livedataex.util.SingleLiveEvent
import edu.yujie.livedataex.util.combine
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private val viewModel by viewModels<MainViewModel> { MainViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.currentName.observe(this) {
            val msg = "$TAG, currentName = $it"
            println(msg)
            findViewById<TextView>(R.id.tvView).text = msg
        }

        lifecycleScope.launch {
            delay(3000L)
            viewModel.setCurrentName("Kotlin")
        }

        //
        StockLiveData.get("Kotlin").observe(this) {
            println("StockLiveData")
        }

        //
        viewModel.getPostCode("台北市").observe(this) {
            println("$TAG:postCode = $it")
        }

        //
        val progressLiveData = MutableLiveData<Int>()
        val countLiveData = MutableLiveData<String>()
        countLiveData.postValue("Loading...")
        val pairLiveData = progressLiveData.combine(countLiveData)
        findViewById<SeekBar>(R.id.sbBar).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                progressLiveData.postValue(seekBar?.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        pairLiveData.observe(this) {
            val msg = "Progress count = ${it.first}"
            findViewById<TextView>(R.id.tvProgress).text = msg
        }
//        progressLiveData.observe(this) {
//            val msg = "Progress count = $it from progressLiveData"
//            println(msg)
//        }

        //SingleLiveEvent
        val singleLiveEvent = SingleLiveEvent<String>()
//        val singleLiveEvent = MutableLiveData<String>()
        findViewById<Button>(R.id.btnSingle).setOnClickListener {
            val msg = "SingleLiveEvent: Button Click"
            singleLiveEvent.postValue(msg)
        }
        singleLiveEvent.observe(this) {
            println("SingleLiveEvent:first")
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
//        singleLiveEvent.observe(this) {
//            println("SingleLiveEvent:second")
//            it.getContentIfNotHandled()?.let {
//                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//            }
//        }

    }
}