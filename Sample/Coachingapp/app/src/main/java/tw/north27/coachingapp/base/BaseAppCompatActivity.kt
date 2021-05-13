package tw.north27.coachingapp.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import tw.north27.coachingapp.ext2.viewBinding

open class BaseAppCompatActivity<T : ViewBinding>(inflater: (LayoutInflater) -> T) : AppCompatActivity() {

    protected val binding by viewBinding(inflater)

    protected val fragManager: FragmentManager
        get() = supportFragmentManager

    protected val fragTrans: FragmentTransaction
        get() = fragManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}