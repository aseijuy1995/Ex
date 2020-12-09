package edu.yujie.rxviewmodel

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

//https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/748074/

class RxViewModelActivity : AppCompatActivity() {
		private val TAG = javaClass.simpleName
		private val viewModel by viewModels<MyViewModel>()

		override fun onCreate(savedInstanceState: Bundle?) {
				super.onCreate(savedInstanceState)
				setContentView(R.layout.activity_main)

				viewModel.dataObservable.subscribe {
						println("$TAG onNext() $it")
						Snackbar.make(window.decorView.rootView, it.toString(), Snackbar.LENGTH_SHORT)
//								.setAnchorView(findViewById<TextView>(R.id.tv_view))
								.show()
				}
		}
}