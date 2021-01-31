package edu.yujie.socketex.finish.base.activity

import androidx.databinding.ViewDataBinding
import edu.yujie.socketex.finish.ext.dataBinding

open class BaseDataBindingAppCompatActivity<T : ViewDataBinding>(layoutId: Int) : BaseAppCompatActivity() {

    protected val binding: T by dataBinding(layoutId)

}