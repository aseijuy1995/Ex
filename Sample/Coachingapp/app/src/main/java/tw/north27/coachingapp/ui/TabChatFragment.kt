package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseFragment

class TabChatFragment : BaseFragment(R.layout.tab_chat) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navFragment = (childFragmentManager.findFragmentById(R.id.frag_container_view) as NavHostFragment)
        val navController = navFragment.navController


    }
}