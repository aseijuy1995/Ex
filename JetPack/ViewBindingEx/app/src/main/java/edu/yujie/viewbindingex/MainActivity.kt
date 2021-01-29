package edu.yujie.viewbindingex

import android.os.Bundle
import edu.yujie.viewbindingex.databinding.ActivityMainBinding

class MainActivity : BaseViewBindingAppCompatActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvView.setOnClickListener {
            binding.tvView.text = "Activity: ${count++}"
        }
    }
}