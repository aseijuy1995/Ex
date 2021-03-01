package tw.north27.coachingapp.module.base.activity

import androidx.databinding.ViewDataBinding
import tw.north27.coachingapp.module.ext.dataBinding

/**
 * 未測試
 * */
open class BaseDataBindingAppCompatActivity<T : ViewDataBinding>(layoutId: Int) : BaseAppCompatActivity() {

    protected val binding by dataBinding<T>(layoutId)

}