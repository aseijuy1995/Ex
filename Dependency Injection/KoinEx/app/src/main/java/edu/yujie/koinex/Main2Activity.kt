package edu.yujie.koinex

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import edu.yujie.koinex.module.Presenter
import org.koin.android.ext.android.inject

class Main2Activity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

//    private val presenter: Presenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        println("$TAG")
//        presenter.show()
//        println("presenter:$presenter")
        println("$TAG:DONE")

        findViewById<ConstraintLayout>(R.id.cLayout).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}