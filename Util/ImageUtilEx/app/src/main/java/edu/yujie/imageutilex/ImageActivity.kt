package edu.yujie.imageutilex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import edu.yujie.imageutilex.databinding.ActivityMainBinding

//https://www.cnblogs.com/dasusu/p/9789389.html

class ImageActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
//        val options = BitmapFactory.Options()
//        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.pic, options)
//        options.inSampleSize = 4
//        binding.ivView.setImageBitmap(bitmap)
//        binding.ivView.loadInfo()
    }


}