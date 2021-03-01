package tw.north27.coachingapp.base

import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import tw.north27.coachingapp.NavGraphDirections
import tw.north27.coachingapp.module.base.fragment.BaseViewBindingFragment

open class BaseCoachingViewBindingFragment<T : ViewBinding>(viewBindingFactory: (LayoutInflater) -> T) : BaseViewBindingFragment<T>(viewBindingFactory) {

    private val loadingDialogNavDirections = NavGraphDirections.actionToFragmentLoadingDialog()

    fun showLoadingDialog() {
        findNavController().navigate(loadingDialogNavDirections)
    }

    fun dismissLoadingDialog() {
        findNavController().navigateUp()
    }

}