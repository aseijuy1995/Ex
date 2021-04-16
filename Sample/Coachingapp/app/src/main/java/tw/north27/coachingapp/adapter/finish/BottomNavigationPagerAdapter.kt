package tw.north27.coachingapp.adapter.finish

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import tw.north27.coachingapp.ui.TabChatFragment
import tw.north27.coachingapp.ui.TabHomeFragment
import tw.north27.coachingapp.ui.TabUserFragment

sealed class BottomNavigationIndex(val index: Int) {
    object HOME : BottomNavigationIndex(0)
    object Chat : BottomNavigationIndex(1)
    object Learn : BottomNavigationIndex(2)
    object Notify : BottomNavigationIndex(3)
    object User : BottomNavigationIndex(4)
}

class BottomNavigationPagerAdapter(fragManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragManager, lifecycle) {

    override fun getItemCount(): Int {
        return fragmentFactory.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentFactory[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

    private val fragmentFactory = mapOf<Int, () -> Fragment>(
//        BottomNavigationIndex.HOME.index to { HomeFragment() },
//        BottomNavigationIndex.Chat.index to { ChatFragment() },
//        BottomNavigationIndex.Learn.index to { LearnFragment() },
//        BottomNavigationIndex.Notify.index to { NotifyFragment() },
//        BottomNavigationIndex.User.index to { UserFragment() },
        BottomNavigationIndex.HOME.index to { TabHomeFragment() },
        BottomNavigationIndex.Chat.index to { TabChatFragment() },
        BottomNavigationIndex.Learn.index to { TabUserFragment() },
        BottomNavigationIndex.Notify.index to { TabHomeFragment() },
        BottomNavigationIndex.User.index to { TabUserFragment() },
    )
}