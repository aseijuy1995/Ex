package tw.north27.coachingapp.module.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import tw.north27.coachingapp.module.ext.viewBinding

/**
 * 已測試
 * */
open class BaseViewBindingAppCompatActivity<T : ViewBinding>(inflater: (LayoutInflater) -> T) : BaseAppCompatActivity() {

    protected val binding by viewBinding(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}