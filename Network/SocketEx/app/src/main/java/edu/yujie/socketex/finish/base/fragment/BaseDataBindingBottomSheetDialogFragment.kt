package edu.yujie.socketex.finish.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import edu.yujie.socketex.R
import edu.yujie.socketex.finish.ext.dataBinding

open class BaseDataBindingBottomSheetDialogFragment<T : ViewDataBinding>(layoutId: Int) : BaseBottomSheetDialogFragment() {

    protected val binding by dataBinding<T>(layoutId)

    protected lateinit var navHostFrag: NavHostFragment

    protected lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        navHostFrag = requireActivity().supportFragmentManager.findFragmentById(R.id.frag_container_view) as NavHostFragment
        navController = navHostFrag.navController
        return binding.root
    }

}