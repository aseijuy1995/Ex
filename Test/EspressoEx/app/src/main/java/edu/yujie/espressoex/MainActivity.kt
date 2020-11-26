package edu.yujie.espressoex

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/**
 * 測試介面性能
 * Teminal:
 * 1.cd \Android\Sdk\platform-tools
 * 2.adb shell dumpsys gfxinfo <PACKAGE_NAME>
 *
 * Stats since: 1009371526870700ns
 * Total frames rendered: 28
 * Janky frames: 10 (35.71%)
 * 50th percentile: 7ms
 * 90th percentile: 150ms
 * 95th percentile: 250ms
 * 99th percentile: 350ms
 * Number Missed Vsync: 2
 * Number High input latency: 5
 * Number Slow UI thread: 5
 * Number Slow bitmap uploads: 0
 * Number Slow issue draw commands: 2
 * Number Frame deadline missed: 5
 *
 * 精確幀時間的訊息
 *
 * */
/**
 * Espresso - 是否上傳分析數據
 * adb shell am instrument -e disableAnalytics true
 * */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            findViewById<TextView>(R.id.textView).text = "Hello Espresso!"
        }

        findViewById<Spinner>(R.id.spinner).also {
            it.adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_items,
                android.R.layout.simple_spinner_item
            )
            it.onItemSelectedListener = ListArrayAdapter(this)
        }

        findViewById<TextView>(R.id.tvDisplayed).visibility = View.GONE

    }

    class ListArrayAdapter(val act: Activity) : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            val data = p0?.getItemAtPosition(p2).toString()
            act.findViewById<TextView>(R.id.tvSpinner).text = data
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        findViewById<TextView>(R.id.tvMenu).text = item?.title
        return true
    }
}