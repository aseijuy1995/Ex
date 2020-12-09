package edu.yujie.rxbindingex

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


//https://github.com/JakeWharton/RxBinding
//https://www.itread01.com/content/1548730656.html
//https://blog.csdn.net/qq_39507260/article/details/84205904

//https://juejin.cn/post/6844903509947596813
//https://segmentfault.com/a/1190000021623958

class RxBindingActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private lateinit var view: View
    private lateinit var btnView: Button
    private lateinit var radioGroup: RadioGroup
    private lateinit var etText: EditText
    private lateinit var scrollView: ScrollView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view = findViewById<View>(android.R.id.content)
        btnView = findViewById<Button>(R.id.btn_view)
        radioGroup = findViewById<RadioGroup>(R.id.radio_group)
        etText = findViewById<EditText>(R.id.et_text)
        scrollView = findViewById<ScrollView>(R.id.scroll_view)
        progressBar = findViewById<ProgressBar>(R.id.progress_bar)

        //rxbinding-view
//        // attachEvents - 根據ViewAttachEvent判斷View是detach還是attach
//        btnView.attachEvents().subscribe {
//            println("$TAG attachEvents()")
//            Snackbar.make(view, "$TAG attachEvents()", Snackbar.LENGTH_SHORT).show()
//        }

//        // attaches - 當View綁定於畫面時呼叫
//        btnView.attaches().subscribe {
//            println("$TAG attaches()")
//            Snackbar.make(view, "$TAG attaches()", Snackbar.LENGTH_SHORT).show()
//        }

//        // detaches - 當View移除於畫面時呼叫
//        btnView.detaches().subscribe {
//            println("$TAG detaches()")
//            Snackbar.make(view, "$TAG detaches()", Snackbar.LENGTH_SHORT).show()
//        }

//        // clicks - 當View被點擊時呼叫
//        btnView.clicks().subscribe {
//            println("$TAG clicks()")
//            Snackbar.make(view, "$TAG clicks()", Snackbar.LENGTH_SHORT).show()
//        }

//        // drags - 當View被拖曳時呼叫(未驗證)
//        btnView.drags().subscribe {
//            println("$TAG drags()")
//            Snackbar.make(view, "$TAG drags()", Snackbar.LENGTH_SHORT).show()
//        }

//        // focusChanges - 當View焦點發生改變時
//        btnView.focusChanges().subscribe {
//            println("$TAG focusChanges()")
//            Snackbar.make(view, "$TAG focusChanges()", Snackbar.LENGTH_SHORT).show()
//        }

//        // changeEvents - 當ViewGroup狀態改變時
//        radioGroup.changeEvents().subscribe {
//            println("$TAG changeEvents()")
//            Snackbar.make(view, "$TAG changeEvents()", Snackbar.LENGTH_SHORT).show()
//        }
//        btnView.clicks().subscribe {
//            radioGroup.addView(RadioButton(this))
//        }

//        // hovers - 懸停事件(未驗證)
//        btnView.hovers().subscribe {
//            println("$TAG hovers()")
//            Snackbar.make(view, "$TAG hovers()", Snackbar.LENGTH_SHORT).show()
//        }

//        // keys - 當鍵盤被按下時呼叫
//        etText.keys().subscribe {
//            println("$TAG keys()")
//            Snackbar.make(view, "$TAG keys()", Snackbar.LENGTH_SHORT).show()
//            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(view.windowToken, 0)
//        }

//        // layoutChangeEvents - 當布局改變時呼叫
//        radioGroup.layoutChangeEvents().subscribe {
//            println("$TAG layoutChangeEvents()")
//            Snackbar.make(view, "$TAG layoutChangeEvents()", Snackbar.LENGTH_SHORT).show()
//        }
//        btnView.clicks().subscribe {
//            radioGroup.addView(RadioButton(this))
//        }

//        // layoutChanges - 當布局參數改變時呼叫
//        radioGroup.layoutChanges().subscribe {
//            println("$TAG layoutChanges()")
//            Snackbar.make(view, "$TAG layoutChanges()", Snackbar.LENGTH_SHORT).show()
//        }
//        btnView.clicks().subscribe {
//            radioGroup.setPadding(5, 5, 5, 5)
//        }

//        // longClicks - 當View長按時呼叫
//        btnView.longClicks().subscribe {
//            println("$TAG longClicks()")
//            Snackbar.make(view, "$TAG longClicks()", Snackbar.LENGTH_SHORT).show()
//        }

//        // scrollChangeEvents - 當View滾動觸發時呼叫
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            scrollView.scrollChangeEvents().subscribe {
//                println("$TAG scrollChangeEvents()")
//                Snackbar.make(view, "$TAG scrollChangeEvents()", Snackbar.LENGTH_SHORT).show()
//            }
//        }

//        // systemUiVisibilityChanges - 當狀態欄&導航欄可見時
//        btnView.systemUiVisibilityChanges().subscribe {
//            println("$TAG systemUiVisibilityChanges()")
//            Snackbar.make(view, "$TAG systemUiVisibilityChanges()", Snackbar.LENGTH_SHORT).show()
//        }

//        // touches - 當View觸摸時呼叫
//        btnView.touches().subscribe {
//            println("$TAG touches()")
//            Snackbar.make(view, "$TAG touches()", Snackbar.LENGTH_SHORT).show() o
//        }

//        // draws - 當View繪製時呼叫
//        btnView.draws()
//            .buffer(3)
//            .subscribe {
//                println("$TAG draws()")
//                Snackbar.make(view, "$TAG draws()", Snackbar.LENGTH_SHORT).show()
//            }

//        // globalLayouts - 當全局布局改變時呼叫
//        btnView.globalLayouts()
//            .buffer(3).subscribe {
//            println("$TAG globalLayouts()")
//            Snackbar.make(view, "$TAG globalLayouts()", Snackbar.LENGTH_SHORT).show()
//        }

//        // preDraws - 當View"將要"繪製時呼叫
//        btnView.preDraws { true }
//            .buffer(3)
//            .subscribe {
//                println("$TAG preDraws()")
//                Snackbar.make(view, "$TAG preDraws()", Snackbar.LENGTH_SHORT).show()
//            }

//        // visibility - 設置View是否可見
//        btnView.visibility().accept(false)

        //--------------------------------------------------------------------------------
        ////rxbinding-widget


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        val itemCamera = menu?.findItem(R.id.item_camera)

//        // menuItem actionViewEvents() - 菜單活動事件(未驗證)
//        itemCamera?.actionViewEvents { true }?.subscribe {
//            println("$TAG actionViewEvents()")
//            Snackbar.make(view, "$TAG actionViewEvents()", Snackbar.LENGTH_SHORT).show()
//        }

//        // menuItem.clicks() - 菜單點擊
//        itemCamera?.clicks()?.subscribe {
//            println("$TAG clicks()")
//            Snackbar.make(view, "$TAG clicks()", Snackbar.LENGTH_SHORT).show()
//        }
        return true
    }

}