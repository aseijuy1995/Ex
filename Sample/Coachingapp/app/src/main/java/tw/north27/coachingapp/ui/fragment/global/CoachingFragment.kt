package tw.north27.coachingapp.ui.fragment.global

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.view.BaseViewBindingFragment
import tw.north27.coachingapp.databinding.FragmentCoachingBinding

class CoachingFragment : BaseViewBindingFragment<FragmentCoachingBinding>(FragmentCoachingBinding::inflate) {

    private lateinit var navHostFrag: NavHostFragment

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        navHostFrag = requireActivity().supportFragmentManager.findFragmentById(R.id.frag_container_view_coaching) as NavHostFragment
        navHostFrag = childFragmentManager.findFragmentById(R.id.frag_container_view_coaching) as NavHostFragment
        navController = navHostFrag.navController

        binding.animBottomBar.onTabSelected = {
            when (it.id) {
                R.id.fragment_home -> navController.navigate(CoachingFragmentDirections.actionFragmentCoachingToFragmentHome())
                R.id.fragment_chat_list -> navController.navigate(CoachingFragmentDirections.actionFragmentCoachingToFragmentChatList())
                R.id.fragment_learn -> navController.navigate(CoachingFragmentDirections.actionFragmentCoachingToFragmentLearn())
                R.id.fragment_notify -> navController.navigate(CoachingFragmentDirections.actionFragmentCoachingToFragmentNotify())
                R.id.fragment_member -> navController.navigate(CoachingFragmentDirections.actionFragmentCoachingToFragmentMember())
            }
        }

//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            when (destination.id) {
//                R.id.frag_home, R.id.frag_store, R.id.frag_mall, R.id.frag_cart, R.id.frag_profile -> {
//                    binding.animBottomBar.isVisible = true
//                }
//                else -> {
//                    binding.animBottomBar.isVisible = false
//                }
//            }
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_nav, menu)
        binding.animBottomBar.setupWithNavController(menu, navController)
    }

}