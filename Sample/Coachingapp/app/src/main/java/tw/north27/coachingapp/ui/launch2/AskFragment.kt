package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import com.yujie.utilmodule.base.BaseFragment
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentAskBinding

class AskFragment : BaseFragment<FragmentAskBinding>(R.layout.fragment_ask) {

    override val viewBind: (View) -> FragmentAskBinding
        get() = FragmentAskBinding::bind

    private val launch2Act: Launch2Activity
        get() = act as Launch2Activity

//    private val viewModel by sharedViewModel<ChatViewModel>()

//    private lateinit var adapter: ChatPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch2Act.doubleClickToExit()
//        adapter = ChatPagerAdapter(this)
//        binding.viewPager2Chat.apply {
//            adapter = this@QuestionAreaFragment.adapter
//            offscreenPageLimit = 2
//        }
//        TabLayoutMediator(binding.tabLayoutChat, binding.viewPager2Chat) { tab: TabLayout.Tab, position: Int ->
//            tab.setIcon(getTabIcon(position))
////            tab.text = getTabText(position)
//        }.attach()
//
//        viewModel.loadChat()
//        viewModel.message.observe(viewLifecycleOwner) {
//            viewModel.receiveChatMessage(it)
//        }
//
//        /**
//         * 測試推播數據
//         * */
//        binding.ivNotify.clicks().observe(viewLifecycleOwner) {
//            viewModel.send(
//                ChatInfo(
//                    id = 5,
//                    sender = UserInfo(
//                        id = -1,
//                        account = "north27",
//                        auth = UserPref.Authority.UNKNOWN,
//                        avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                        name = "阿吉"
//                    ),
//                    recipient = UserInfo(
//                        id = 5,
//                        account = "lbj7871",
//                        auth = UserPref.Authority.UNKNOWN,
//                        avatarPath = "https://cf.shopee.tw/file/66f6a55ddd243f22b78c99847406b516",
//                        name = "lbj7871"
//                    ),
//                    sendTime = "03:52",
//                    chatType = ChatType.TEXT,
//                    text = "測試推播 - 測試推播 - 測試推播 - 測試推播 - 測試推播",
//                    read = ChatRead.UN_READ,
//                    unReadCount = 1,
//                    isSound = true
//                )
//            )
//        }
//
//        binding.floatingActionButtonChat.clicks().observe(viewLifecycleOwner) {
//            viewModel.listScrollToTop(true)
//        }
//
//        viewModel.listScrollToTop.observe(viewLifecycleOwner) {
//            binding.appBarLayoutChat.setExpanded(true)
//        }
//
//    }
//
//    private fun getTabIcon(position: Int) = when (position) {
//        ChatReadIndex.ALL.index -> R.drawable.ic_baseline_email_24_white
//        ChatReadIndex.HAVE_READ.index -> R.drawable.ic_baseline_mark_email_read_24_white
//        ChatReadIndex.UN_READ.index -> R.drawable.ic_baseline_mark_email_unread_24_white
//        else -> throw IndexOutOfBoundsException()
//    }
//
//    private fun getTabText(position: Int) = when (position) {
//        ChatReadIndex.ALL.index -> getString(R.string.all)
//        ChatReadIndex.HAVE_READ.index -> getString(R.string.have_read)
//        ChatReadIndex.UN_READ.index -> getString(R.string.un_read)
//        else -> null
    }
}