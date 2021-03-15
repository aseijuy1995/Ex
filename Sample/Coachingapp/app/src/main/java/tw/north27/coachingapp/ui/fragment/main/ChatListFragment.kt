package tw.north27.coachingapp.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.*
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.chat.ChatListViewModel
import tw.north27.coachingapp.databinding.FragmentChatListBinding
import tw.north27.coachingapp.ext.start
import tw.north27.coachingapp.ext.stop
import tw.north27.coachingapp.ext.viewBinding

class ChatListFragment : BaseFragment(R.layout.fragment_chat_list) {

    private val binding by viewBinding<FragmentChatListBinding>(FragmentChatListBinding::bind)

    private val viewModel by sharedViewModel<ChatListViewModel>()

    private val adapter = ChatListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val type = arguments?.getSerializable(KEY_CHAT_READ_TYPE) ?: return
        binding.itemChatShinner.shimmerFrameLayoutChat.start()
        binding.rvChat.adapter = adapter
        binding.rvChat.apply {
            addItemDecoration(DividerItemDecoration(cxt, LinearLayoutManager.VERTICAL).apply {
                setDrawable(ContextCompat.getDrawable(cxt, R.drawable.shape_size_height_1_solid_gray) ?: return)
            })
            adapter = this@ChatListFragment.adapter
        }
        viewModel.loadChat(type as ChatReadIndex)

        viewModel.chatList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        //Refresh
        binding.smartRefreshLayoutChat.setOnRefreshListener {
            viewModel.loadChat(type as ChatReadIndex)
        }

        viewModel.toast.observe(viewLifecycleOwner, ::onToastObs)


        viewModel.messageRelay.subscribeWithRxLife {
            viewModel.refreshChatList(it)
        }

        //接收通知滑動置頂
        binding.rvChat.adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
//                super.onItemRangeInserted(positionStart, itemCount)
                binding.rvChat.scrollToPosition(0)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
//                super.onItemRangeRemoved(positionStart, itemCount)
                binding.rvChat.smoothScrollToPosition(itemCount)
            }
        })

    }

    private fun onToastObs(pair: Pair<ChatListViewModel.ToastType, String>) {
        when (pair.first) {
            ChatListViewModel.ToastType.LOAD_CHAT -> {
                binding.smartRefreshLayoutChat.finishRefresh()
                binding.itemChatShinner.shimmerFrameLayoutChat.stop()
                Snackbar.make(binding.root, pair.second, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}