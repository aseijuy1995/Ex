package edu.yujie.databindingex

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import edu.yujie.databindingex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                .apply {
//                    user = User("Test", "User", true)
//                    list = listOf(1, 2, 3)
//                    handlers = MyHandlers()
//                    bindingAdapterOnToastClickStr = "BindingAdapter setOnToastClick"
//                    isError = false
                    lifecycleOwner = this@MainActivity
                    viewModel = this@MainActivity.viewModel
                }
//        val binding = ActivityMainBinding.inflate(layoutInflater).apply {
//                    user = User("Test", "User")
//                }
//        setContentView(binding.root)

        binding.textView.setOnToastClick("Hello, Kotlin")
    }
}