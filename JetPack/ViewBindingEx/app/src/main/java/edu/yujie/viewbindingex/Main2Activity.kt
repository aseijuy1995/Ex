package edu.yujie.viewbindingex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.yujie.viewbindingex.databinding.ActivityMain2Binding

class Main2Activity : AppCompatActivity(R.layout.activity_main2) {
    private val binding by viewBinding {
        ActivityMain2Binding.inflate(layoutInflater).apply {
            setContentView(root)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tvText.text = "ViewBinding"
    }
}

