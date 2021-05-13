package tw.north27.coachingapp.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import tw.north27.coachingapp.ui2.fragment.main.ChatListFragment

const val KEY_CHAT_READ_TYPE = "KEY_CHAT_READ_TYPE"

enum class ChatReadIndex(val index: Int) {
    ALL(0), HAVE_READ(1), UN_READ(2)
}

class ChatPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return fragmentFactory.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentFactory[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

    private val fragmentFactory = mapOf<Int, () -> Fragment>(
        ChatReadIndex.ALL.index to {
            ChatListFragment().apply { arguments = bundleOf(KEY_CHAT_READ_TYPE to ChatReadIndex.ALL) }
        },
        ChatReadIndex.HAVE_READ.index to {
            ChatListFragment().apply { arguments = bundleOf(KEY_CHAT_READ_TYPE to ChatReadIndex.HAVE_READ) }
        },
        ChatReadIndex.UN_READ.index to {
            ChatListFragment().apply { arguments = bundleOf(KEY_CHAT_READ_TYPE to ChatReadIndex.UN_READ) }
        }
    )
}