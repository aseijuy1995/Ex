package edu.yujie.constraintlayoutex

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val viewStub = findViewById<ViewStub>(R.id.viewStub)
        val button1 = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)
        var view: View? = null
        button1.setOnClickListener {
            try {
                view = viewStub.inflate()
            } catch (e: Exception) {
                viewStub.visibility = View.VISIBLE
            }
            view?.findViewById<ImageView>(R.id.ivView)?.setImageResource(R.drawable.ic_viewmodel)
        }
        button2.setOnClickListener {
            viewStub.visibility = View.INVISIBLE
        }

    }

}