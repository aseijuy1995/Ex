package edu.yujie.rxbindingex

import android.Manifest
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.checked
import com.tbruyelle.rxpermissions3.RxPermissions


//https://github.com/JakeWharton/RxBinding
//https://blog.csdn.net/qq_39507260/article/details/84205904
//https://segmentfault.com/a/1190000021623958

class RxBindingActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private lateinit var view: View
    private lateinit var btnView: Button
    private lateinit var radioGroup: RadioGroup
    private lateinit var etText: EditText
    private lateinit var scrollView: ScrollView
    private lateinit var listView: ListView
    private lateinit var rvView: RecyclerView
    private lateinit var spinner: Spinner
    private lateinit var autoTvView: AutoCompleteTextView
    private lateinit var radioBtn: RadioButton
    private lateinit var radioBtn2: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view = findViewById<View>(android.R.id.content)
        btnView = findViewById<Button>(R.id.btn_view)
        radioGroup = findViewById<RadioGroup>(R.id.radio_group)
        etText = findViewById<EditText>(R.id.et_text)
        scrollView = findViewById<ScrollView>(R.id.scroll_view)
        listView = findViewById<ListView>(R.id.list_view)
        rvView = findViewById<RecyclerView>(R.id.rv_view)
        spinner = findViewById<Spinner>(R.id.spinner)
        autoTvView = findViewById<AutoCompleteTextView>(R.id.auto_tv_view)
        radioBtn = findViewById<RadioButton>(R.id.radio_btn)
        radioBtn2 = findViewById<RadioButton>(R.id.radio_btn2)

        val list = mutableListOf<Data>()
        for (i in 0..100) {
            list.add(Data(name = "name = $i"))
        }
        //listView
        listView.adapter = MyListAdapter(list)
        //spinner
        spinner.adapter = MyListAdapter(list)
        //rvView
        rvView.apply {
            layoutManager = LinearLayoutManager(this@RxBindingActivity)
            adapter = MyRvListAdapter(list)
        }

        /**
         * rxbinding
         * view
         * */
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
        /**
         * rxbinding
         * widget
         * */
//        // scrollEvents - ListView滾動事件
//        listView.scrollEvents().subscribe {
//            println("$TAG scrollEvents()")
//            Snackbar.make(view, "$TAG scrollEvents()", Snackbar.LENGTH_SHORT).show()
//        }

//        // dataChanges - Adapter數據改變時呼叫
//        listView.adapter.dataChanges().subscribe {
//            println("$TAG dataChanges()")
//            Snackbar.make(view, "$TAG dataChanges()", Snackbar.LENGTH_SHORT).show()
//        }

//        // itemClickEvents - Adapter項目點擊狀態改變時
//        listView.itemClickEvents().subscribe {
//            println("$TAG itemClickEvents()")
//            Snackbar.make(view, "$TAG itemClickEvents()", Snackbar.LENGTH_SHORT).show()
//        }

//        // itemClicks - Adapter項目點擊時呼叫
//        listView.itemClicks().subscribe {
//            println("$TAG itemClicks()")
//            Snackbar.make(view, "$TAG itemClicks()", Snackbar.LENGTH_SHORT).show()
//        }

//        // itemLongClickEvents - Adapter項目長按狀態改變時
//        listView.itemLongClickEvents().subscribe {
//            println("$TAG itemLongClickEvents()")
//            Snackbar.make(view, "$TAG itemLongClickEvents()", Snackbar.LENGTH_SHORT).show()
//        }

//        // itemLongClicks - Adapter項目長按時呼叫
//        listView.itemLongClicks().subscribe {
//            println("$TAG itemLongClicks()")
//            Snackbar.make(view, "$TAG itemLongClicks()", Snackbar.LENGTH_SHORT).show()
//        }

//        // itemSelections - Adapter項目選擇時呼叫
//        spinner.itemSelections().subscribe {
//            println("$TAG itemSelections()")
//            Snackbar.make(view, "$TAG itemSelections()", Snackbar.LENGTH_SHORT).show()
//        }

//        // selectionEvents - Adapter項目選擇狀態改變時
//        spinner.selectionEvents().subscribe {
//            println("$TAG selectionEvents()")
//            Snackbar.make(view, "$TAG selectionEvents()", Snackbar.LENGTH_SHORT).show()
//        }

//        // itemClickEvents - 比對到相同數據時呼叫
//        autoTvView.itemClickEvents().subscribe {
//            println("$TAG itemClickEvents()")
//            Snackbar.make(view, "$TAG itemClickEvents()", Snackbar.LENGTH_SHORT).show()
//        }

//        // checkedChanges - 當View勾選改變時呼叫
//        radioBtn.checkedChanges().subscribe {
//            println("$TAG checkedChanges()")
//            Snackbar.make(view, "$TAG checkedChanges()", Snackbar.LENGTH_SHORT).show()
//        }

        // PopupMenu.dismisses()// 當彈窗點擊消失時呼叫
        // PopupMenu.itemClicks()// 當彈窗點擊時呼叫

        // checked - 設置View勾選
        radioGroup.checked().accept(R.id.radio_btn2)

        // RatingBar.ratingChangeEvents()// 星級滾動條進度改變事件
        // RatingBar.ratingChanges()// 星級滾動條進度改變事件

        // SearchView.query()// 搜索框
        // SearchView.queryTextChangeEvents()// 搜索框
        // SearchView.queryTextChanges()// 搜索框

        // SeekBar.changeEvents()// 進度條進度改變
        // SeekBar.changes()// 進度條進度改變
        // SeekBar.userChanges()// 進度條進度改變
        // SeekBar.systemChanges()// 進度條進度改變

        // TextView.afterTextChangeEvents()// 文本改变
        // TextView.beforeTextChangeEvents()// 文本改变
        // TextView.editorActionEvents()// 文本改变
        // TextView.editorActions()// 編輯狀態改改變
        // TextView.textChangeEvents()// 文本改变
        // TextView.textChanges()// 文本改变

        // Toolbar.itemClicks()// Toolbar中子項和導航點擊事件
        // Toolbar.navigationClicks()// Toolbar中子項和導航點擊事件

        //--------------------------------------------------------------------------------
        /**
         * rxbinding-core
         * */
        // NestedScrollView.scrollChangeEvents()// 當View滾動觸發時呼叫

        //--------------------------------------------------------------------------------
        /**
         * rxbinding-appcompat
         * */
        // ActionMenuView.itemClicks()// 項目點擊時呼叫
        // PopupMenu.dismisses()
        // PopupMenu.itemClicks()
        // SearchView.queryTextChangeEvents()
        // SearchView.queryTextChanges()
        // SearchView.query()
        // Toolbar.itemClicks()
        // Toolbar.navigationClicks()

        //--------------------------------------------------------------------------------
        /**
         * rxbinding-drawerlayout
         * */
        // DrawerLayout.drawerOpen()
        // DrawerLayout.open()

        //--------------------------------------------------------------------------------
        /**
         * rxbinding-leanback
         * */
        // SearchBar.searchQueryChangeEvents()
        // SearchBar.searchQueryChanges()
        // SearchEditText.keyboardDismisses()

        //--------------------------------------------------------------------------------
        /**
         * rxbinding-recyclerview
         * */
        // <T : Adapter<out ViewHolder>> T.dataChanges()
        // RecyclerView.childAttachStateChangeEvents()
        // RecyclerView.flingEvents()
        // RecyclerView.scrollEvents()
        // RecyclerView.scrollStateChanges()

        //--------------------------------------------------------------------------------
        /**
         * rxbinding-slidingpanelayout
         * */
        // SlidingPaneLayout.open()
        // SlidingPaneLayout.panelOpens()
        // SlidingPaneLayout.panelSlides()

        //--------------------------------------------------------------------------------
        /**
         * rxbinding-swiperefreshlayout
         * */
        // SwipeRefreshLayout.refreshes()

        //--------------------------------------------------------------------------------
        /**
         * rxbinding-viewpager
         * */
        // ViewPager.pageScrollStateChanges()
        // ViewPager.pageScrollEvents()
        // ViewPager.pageSelections()

        //--------------------------------------------------------------------------------
        /**
         * rxbinding-viewpager2
         * */
        // ViewPager2.pageScrollStateChanges()
        // ViewPager2.pageScrollEvents()
        // ViewPager2.pageSelections()

        //--------------------------------------------------------------------------------
        /**
         * RxBinding & RxPermission
         * */

        btnView.clicks()
            .compose(RxPermissions(this).ensure(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO))
            .subscribe {
                Snackbar.make(view, "$TAG it = $it", Snackbar.LENGTH_SHORT).show()
            }

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