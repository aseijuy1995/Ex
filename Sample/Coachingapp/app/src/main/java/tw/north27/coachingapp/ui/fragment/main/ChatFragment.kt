package tw.north27.coachingapp.ui.fragment.main

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding4.view.clicks
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.ChatPagerAdapter
import tw.north27.coachingapp.adapter.ChatReadIndex
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.chat.ChatViewModel
import tw.north27.coachingapp.databinding.FragmentChatBinding
import tw.north27.coachingapp.ext.viewBinding
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.model.result.ChatRead
import tw.north27.coachingapp.model.result.ChatType
import tw.north27.coachingapp.model.result.UserInfo

class ChatFragment : BaseFragment(R.layout.fragment_chat) {

    private val binding by viewBinding<FragmentChatBinding>(FragmentChatBinding::bind)

    private lateinit var adapter: ChatPagerAdapter

    private val viewModel by sharedViewModel<ChatViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ChatPagerAdapter(this)
        binding.viewPager2Chat.adapter = adapter

        TabLayoutMediator(binding.tabLayoutChat, binding.viewPager2Chat) { tab: TabLayout.Tab, position: Int ->
            tab.setIcon(getTabIcon(position))
//            tab.text = getTabText(position)
        }.attach()

        viewModel.loadChat()

        viewModel.message.subscribeWithRxLife {
            viewModel.refreshChatList(it)
        }

        /**
         * 測試推播數據
         * */
        binding.ivNotify.clicks().subscribeWithRxLife {
            viewModel.send(
//                ChatInfo(
//                    id = 2,
//                    sender = UserInfo(
//                        id = 2,
//                        account = "turboted",
//                        avatarPath = "https://www.actphoto.org.tw/themes/zh-tw/assets/images/default_member.jpg",
//                        name = "turboted"
//                    ),
//                    recipient = UserInfo(
//                        id = -1,
//                        account = "jie001",
//                        avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                        name = "阿吉"
//                    ),
//                    sendTime = "03:50",
//                    chatType = ChatType.TEXT,
//                    text = "測試推播 - 測試推播 - 測試推播 - 測試推播 - 測試推播",
//                    read = ChatRead.UN_READ,
//                    unReadCount = 5
//                )
                ChatInfo(
                    id = 5,
                    sender = UserInfo(
                        id = 5,
                        account = "lbj7871",
                        avatarPath = "https://cf.shopee.tw/file/66f6a55ddd243f22b78c99847406b516",
                        name = "lbj7871"
                    ),
                    recipient = UserInfo(
                        id = -1,
                        account = "jie001",
                        avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                        name = "阿吉"
                    ),
                    sendTime = "03:52",
                    chatType = ChatType.TEXT,
                    text = "測試推播 - 測試推播 - 測試推播 - 測試推播 - 測試推播",
                    read = ChatRead.UN_READ,
                    unReadCount = 1,
                    sound = true
                )
            )

        }

//        binding.ivAutoRenew.clicks().subscribeWithRxLife {
//        }

        binding.floatingActionButtonChat.clicks().subscribeWithRxLife {
            binding.appBarLayout.setExpanded(true)
            viewModel.scrollToTop(true)
        }
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