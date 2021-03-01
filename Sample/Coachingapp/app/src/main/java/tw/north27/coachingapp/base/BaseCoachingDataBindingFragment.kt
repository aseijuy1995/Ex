package tw.north27.coachingapp.base

import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import tw.north27.coachingapp.NavGraphDirections
import tw.north27.coachingapp.module.base.fragment.BaseDataBindingFragment

open class BaseCoachingDataBindingFragment<T : ViewDataBinding>(private val layoutId: Int) : BaseDataBindingFragment<T>(layoutId) {

    private val loadingDialogNavDirections = NavGraphDirections.actionToFragmentLoadingDialog()

    fun showLoadingDialog() {
        findNavController().navigate(loadingDialogNavDirections)
    }

    fun dismissLoadingDialog() {
        findNavController().navigateUp()
    }

}