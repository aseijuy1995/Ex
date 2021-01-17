package edu.yujie.rxlifecycleex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import edu.yujie.rxlifecycleex.databinding.ActivityRxLifecycleBinding

class RxLifecycleActivity2 : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    private lateinit var binding: ActivityRxLifecycleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityRxLifecycleBinding>(this, R.layout.activity_rx_lifecycle)

    }


}