package edu.yujie.constraintlayoutex

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Placeholder
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var layout: ConstraintLayout
    private lateinit var placeHolder2: Placeholder
    private lateinit var placeHolder3: Placeholder
    private lateinit var placeHolder4: Placeholder
    private lateinit var placeHolder5: Placeholder
    private lateinit var placeHolder6: Placeholder
    private lateinit var placeHolderList: List<Placeholder>
    private lateinit var viewIdList: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_placeholder)
        findViewById<ImageButton>(R.id.iBtn1).setOnClickListener(this)
        findViewById<ImageButton>(R.id.iBtn2).setOnClickListener(this)
        findViewById<ImageButton>(R.id.iBtn3).setOnClickListener(this)
        findViewById<ImageButton>(R.id.iBtn4).setOnClickListener(this)
        //
        layout = findViewById<ConstraintLayout>(R.id.cLayout)
        placeHolder2 = findViewById<Placeholder>(R.id.placeHolder2)
        placeHolder3 = findViewById<Placeholder>(R.id.placeHolder3)
        placeHolder4 = findViewById<Placeholder>(R.id.placeHolder4)
        placeHolder5 = findViewById<Placeholder>(R.id.placeHolder5)
        placeHolder6 = findViewById<Placeholder>(R.id.placeHolder6)
        placeHolderList = listOf(placeHolder3, placeHolder4, placeHolder5, placeHolder6)
        viewIdList = listOf(R.id.iBtn1, R.id.iBtn2, R.id.iBtn3, R.id.iBtn4)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iBtn1, R.id.iBtn2, R.id.iBtn3, R.id.iBtn4 -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(layout)
                }
                if (placeHolderList.all { it.content != null }) {
                    val index = viewIdList.indexOf(v?.id)
                    placeHolder2.setContentId(v?.id)
                    placeHolderList[index].setContentId(-1)
                } else {
                    placeHolderList.first { it.content == null }
                        .setContentId(placeHolder2.content.id)
                    thread {
                        sleep(1000L)
                        runOnUiThread {
                            placeHolder2.setContentId(v?.id)
                        }
                    }
                }
            }
        }
    }
}