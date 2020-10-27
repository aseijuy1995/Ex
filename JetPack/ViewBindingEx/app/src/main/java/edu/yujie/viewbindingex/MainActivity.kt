package edu.yujie.viewbindingex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.yujie.viewbindingex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}