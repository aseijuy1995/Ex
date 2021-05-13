package tw.north27.coachingapp.ext2

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import timber.log.Timber
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <T : ViewBinding> Activity.viewBinding(crossinline viewBindingFactory: (LayoutInflater) -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { viewBindingFactory.invoke(layoutInflater) }

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T): FragmentViewBindingBindProperty<T> =
    FragmentViewBindingBindProperty(this, viewBindingFactory)

class FragmentViewBindingBindProperty<T : ViewBinding>(private val fragment: Fragment, private val viewBindingFactory: (View) -> T) : ReadOnlyProperty<Fragment, T> {
    private var _binding: T? = null

    init {
        try {
            fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
                val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner?> {
                    val viewLifecycleOwner = it ?: return@Observer
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            _binding = null
                        }
                    })
                }

                override fun onCreate(owner: LifecycleOwner) {
                    fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerLiveDataObserver)
                }

                override fun onDestroy(owner: LifecycleOwner) {
                    fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerLiveDataObserver)
                }
            })
        } catch (e: Exception) {
            Timber.e(e)
            e.printStackTrace()
        }
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED))
            throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")

        return _binding ?: viewBindingFactory(fragment.requireView()).also { _binding = it }
    }
}

/**
 * 未驗證
 * */
fun <T : ViewBinding> Fragment.viewBinding(inflater: (LayoutInflater) -> T): FragmentViewBindingInflaterProperty<T> =
    FragmentViewBindingInflaterProperty(this, inflater)

class FragmentViewBindingInflaterProperty<T : ViewBinding>(private val fragment: Fragment, private val viewBindingFactory: (LayoutInflater) -> T) : ReadOnlyProperty<Fragment, T> {
    private var _binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner> {
                val viewLifecycleOwner = it ?: return@Observer
                viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                    override fun onDestroy(owner: LifecycleOwner) {
                        _binding = null
                    }
                })
            }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerLiveDataObserver)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerLiveDataObserver)
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED))
            throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")

        return _binding ?: viewBindingFactory(fragment.layoutInflater).also { _binding = it }
    }
}

///**
// * 未驗證
// * */
//fun <T : ViewBinding, VH : RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.viewBinding(
//    viewBindingFactory: (LayoutInflater, ViewGroup, Boolean) -> T,
//    inflate: LayoutInflater,
//    viewGroup: ViewGroup,
//    attachToParent: Boolean
//) = lazy(LazyThreadSafetyMode.NONE) { viewBindingFactory.invoke(inflate, viewGroup, attachToParent) }
