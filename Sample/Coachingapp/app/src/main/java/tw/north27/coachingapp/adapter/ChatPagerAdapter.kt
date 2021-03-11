package tw.north27.coachingapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import tw.north27.coachingapp.ui.fragment.main.ChatListFragment
import tw.north27.coachingapp.ui.fragment.main.ChatListFragment2

const val CHAT_INDEX = 0
const val USER_INDEX = 1

class ChatPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return fragmentFactory.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentFactory[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

    private val fragmentFactory = mapOf<Int, () -> Fragment>(
        CHAT_INDEX to { ChatListFragment() },
        USER_INDEX to { ChatListFragment2() }
    )
}