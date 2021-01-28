package edu.yujie.socketex.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import edu.yujie.socketex.R
import edu.yujie.socketex.base.finish.base.BaseViewBindingAppCompatActivity
import edu.yujie.socketex.databinding.ActivitySocketBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


//https://juejin.cn/post/6844903461796970504
//https://notfalse.net/7/three-way-handshake
//https://www.jianshu.com/p/089fb79e308b
//https://www.jianshu.com/p/8175f51e662c
//https://blog.csdn.net/xlh1191860939/article/details/103216735
//https://www.jianshu.com/p/45d27f3e1196
//https://www.jianshu.com/p/a6d086a3997d

//class SocketActivity : BaseAppCompatActivity() {
class SocketActivity : BaseViewBindingAppCompatActivity<ActivitySocketBinding>(ActivitySocketBinding::inflate) {

//    override val layoutId: Int
//        get() = R.layout.activity_socket

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.frag_container_view).navigateUp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}

class FragmentViewBindingProperty<T : ViewBinding>(
    private val frag: Fragment,
    private val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    init {
        frag.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerObserver = Observer<LifecycleOwner> {
                val viewLifecycleOwner = it ?: return@Observer
                viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                    override fun onDestroy(owner: LifecycleOwner) {
                        super.onDestroy(owner)
                        binding = null
                    }
                })
            }

            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                frag.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerObserver)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                frag.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerObserver)
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (binding != null) {
            return binding!!
        }
        val lifecycle = frag.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")
        }
        return viewBindingFactory(thisRef.requireView())
    }
}