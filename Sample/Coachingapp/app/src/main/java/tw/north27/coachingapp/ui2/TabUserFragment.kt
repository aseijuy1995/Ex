package tw.north27.coachingapp.ui2

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseFragment

class TabUserFragment : BaseFragment(R.layout.tab_user) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navFragment = (childFragmentManager.findFragmentById(R.id.frag_container_view) as NavHostFragment)
        val navController = navFragment.navController


    }
}