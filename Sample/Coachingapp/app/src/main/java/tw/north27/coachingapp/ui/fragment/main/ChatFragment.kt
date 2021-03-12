package tw.north27.coachingapp.ui.fragment.main

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.ChatPagerAdapter
import tw.north27.coachingapp.adapter.ChatReadIndex
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.databinding.FragmentChatBinding
import tw.north27.coachingapp.ext.viewBinding

class ChatFragment : BaseFragment(R.layout.fragment_chat) {

    private val binding by viewBinding<FragmentChatBinding>(FragmentChatBinding::bind)

    private lateinit var adapter: ChatPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ChatPagerAdapter(this)
        binding.viewPager2Chat.adapter = adapter

        TabLayoutMediator(binding.tabLayoutChat, binding.viewPager2Chat) { tab: TabLayout.Tab, position: Int ->
            tab.setIcon(getTabIcon(position))
//            tab.text = getTabText(position)
        }.attach()
    }

    private fun getTabText(position: Int) = when (position) {
        ChatReadIndex.ALL.index -> getString(R.string.all)
        ChatReadIndex.HAVE_READ.index -> getString(R.string.have_read)
        ChatReadIndex.UN_READ.index -> getString(R.string.un_read)
        else -> null
    }

    private fun getTabIcon(position: Int) = when (position) {
        ChatReadIndex.ALL.index -> R.drawable.ic_baseline_email_24_white
        ChatReadIndex.HAVE_READ.index -> R.drawable.ic_baseline_mark_email_read_24_white
        ChatReadIndex.UN_READ.index -> R.drawable.ic_baseline_mark_email_unread_24_white
        else -> throw IndexOutOfBoundsException()
    }
}