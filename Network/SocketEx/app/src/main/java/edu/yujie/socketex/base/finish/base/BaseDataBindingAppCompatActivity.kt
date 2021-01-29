package edu.yujie.socketex.base.finish.base

import androidx.databinding.ViewDataBinding
import edu.yujie.socketex.base.finish.ext.dataBinding

open class BaseDataBindingAppCompatActivity<T : ViewDataBinding>(layoutId: Int) : BaseAppCompatActivity() {

    protected val binding: T by dataBinding(layoutId)

}