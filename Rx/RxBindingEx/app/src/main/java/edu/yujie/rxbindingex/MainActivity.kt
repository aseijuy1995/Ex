package edu.yujie.rxbindingex

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import com.tbruyelle.rxpermissions3.RxPermissions

//https://github.com/JakeWharton/RxBinding
//https://juejin.cn/post/6844903509947596813
//https://segmentfault.com/a/1190000021623958

class MainActivity : AppCompatActivity() {
		private val TAG = javaClass.simpleName

		override fun onCreate(savedInstanceState: Bundle?) {
				super.onCreate(savedInstanceState)
				setContentView(R.layout.activity_main)

				val btnView = findViewById<Button>(R.id.btn_view)
				val etText = findViewById<EditText>(R.id.et_text)
				val rvView = findViewById<RecyclerView>(R.id.rv_view)

				//Button
//				// clicks 點擊
//				var i = 0
//				btnView.clicks()
//						.throttleFirst(500, TimeUnit.MILLISECONDS)
//						.subscribe {
//								Snackbar.make(window.decorView, "$TAG clicks() i = ${++i}", Snackbar.LENGTH_SHORT).show()
//						}

//				// longClicks 長按
//				var i = 0
//				btnView.longClicks()
//						.subscribe {
//								Snackbar.make(window.decorView, "$TAG longClicks() i = ${++i}", Snackbar.LENGTH_SHORT).show()
//						}

//				// draws - OnDrawListener() 繪製
//				var i = 0
//				btnView.draws()
//						.throttleFirst(500, TimeUnit.MILLISECONDS)
//						.subscribe {
//								Snackbar.make(window.decorView, "$TAG draws() i = ${++i}", Snackbar.LENGTH_SHORT).show()o
//						}

//				// drags - OnDragListener() 拖曳
//				var i = 0
//				btnView.drags { true }
//						.throttleFirst(500, TimeUnit.MILLISECONDS)
//						.subscribe {
//								Snackbar.make(window.decorView, "$TAG clicks() i = ${++i}", Snackbar.LENGTH_SHORT).show()
//						}

//				// layoutChanges - OnLayoutChangeListener 布局改變
//				var i = 0
//				btnView.layoutChanges()
//						.subscribe {
//								Snackbar.make(window.decorView, "$TAG clicks() i = ${++i}", Snackbar.LENGTH_SHORT).show()
//						}

//				// scrollChangeEvents - OnScrollChangeListener 滑動改變
//				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//						var i = 0
//						btnView.scrollChangeEvents()
//								.subscribe {
//										Snackbar.make(window.decorView, "$TAG clicks() i = ${++i}", Snackbar.LENGTH_SHORT).show()
//								}
//				}

				//--------------------------------------------------------------------------------
				//EditText

//				// textChanges - addTextChangedListener 文字改變
//				etText.textChanges().subscribe {
//						Snackbar.make(window.decorView, "$TAG textChanges() it = $it", Snackbar.LENGTH_SHORT).show()
//				}

				//--------------------------------------------------------------------------------
				//RecyclerView

//				// scrollStateChanges - 滾動監聽
//				rvView.scrollStateChanges().subscribe {  }

//				// scrollEvents - 滾動事件
//				rvView.scrollEvents()

//				// childAttachStateChangeEvents - 子項目的Detached狀態
//				rvView.childAttachStateChangeEvents()

				//--------------------------------------------------------------------------------

				//  RxBinding & RxPermission
				btnView.clicks()
						.compose(RxPermissions(this).ensure(android.Manifest.permission.CAMERA))
						.subscribe {
								Snackbar.make(window.decorView.rootView, if (it) "Allow" else "Deny", Snackbar.LENGTH_SHORT).show()
						}
		}
}