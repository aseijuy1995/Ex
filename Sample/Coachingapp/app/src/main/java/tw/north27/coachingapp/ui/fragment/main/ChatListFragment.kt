package tw.north27.coachingapp.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.recyclerview.scrollStateChanges
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.NavGraphDirections
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.*
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.chat.ChatViewModel
import tw.north27.coachingapp.databinding.FragmentChatListBinding
import tw.north27.coachingapp.ext.start
import tw.north27.coachingapp.ext.stop
import tw.north27.coachingapp.ext.viewBinding
import tw.north27.coachingapp.model.result.ChatRead

class ChatListFragment : BaseFragment(R.layout.fragment_chat_list) {

    private val binding by viewBinding<FragmentChatListBinding>(FragmentChatListBinding::bind)

    private val viewModel by sharedViewModel<ChatViewModel>()

    private val adapter = ChatListAdapter()

    private lateinit var type: ChatReadIndex

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type = (arguments?.getSerializable(KEY_CHAT_READ_TYPE) as ChatReadIndex)
        binding.rvChat.apply {
            addItemDecoration(DividerItemDecoration(cxt, LinearLayoutManager.VERTICAL).apply {
                setDrawable(ContextCompat.getDrawable(cxt, R.drawable.shape_size_height_1_solid_gray) ?: return)
            })
            adapter = this@ChatListFragment.adapter
        }

        viewModel.chatListState.observe(viewLifecycleOwner) {
            if (it) {
                binding.itemChatShinner.shimmerFrameLayoutChat.start()
                binding.rvChat.isVisible = false
                binding.itemEmpty.clEmpty.isVisible = false
            } else {
                binding.itemChatShinner.shimmerFrameLayoutChat.stop()
                viewModel.scrollToTop(true)
            }
        }

        viewModel.chatList.observe(viewLifecycleOwner) {
            val list = when (type) {
                ChatReadIndex.ALL -> it
                ChatReadIndex.HAVE_READ -> it?.filter { it.read == ChatRead.HAVE_READ }
                ChatReadIndex.UN_READ -> it?.filter { it.read == ChatRead.UN_READ }
            }
            when {
                list == null -> {
                    binding.rvChat.isVisible = false
                    binding.itemEmpty.clEmpty.isVisible = false
                }
                list.isEmpty() -> {
                    binding.rvChat.isVisible = false
                    binding.itemEmpty.clEmpty.isVisible = true
                }
                else -> {
                    binding.rvChat.isVisible = true
                    binding.itemEmpty.clEmpty.isVisible = false
                    adapter.submitList(list)
                    viewModel.scrollToTop(true)
                }
            }
        }

        //Refresh
        binding.smartRefreshLayoutChat.setOnRefreshListener {
            viewModel.loadChat()
        }

        //接收通知滑動置頂
        binding.rvChat.adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                viewModel.scrollToTop(true)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                binding.rvChat.smoothScrollToPosition(itemCount)
            }
        })

        viewModel.scrollToTop.observe(viewLifecycleOwner) {
            binding.rvChat.scrollToPosition(0)
            binding.rvChat.postDelayed({
                binding.rvChat.smoothScrollToPosition(0)
            }, 500)
        }

        binding.rvChat.scrollStateChanges().subscribeWithRxLife {
            when (it) {
                SCROLL_STATE_IDLE -> {
                    viewModel.shoeFab(true)
                }
                SCROLL_STATE_DRAGGING -> {
                    viewModel.shoeFab(false)
                }
            }
        }

        viewModel.toast.observe(viewLifecycleOwner, ::onToastObs)

        //Item Click
        adapter.soundClickRelay.subscribeWithRxLife {
            showLoadingDialog()
            viewModel.switchChatSound(type, it.second)
        }

        adapter.deleteClickRelay.subscribeWithRxLife {
            showLoadingDialog()
            viewModel.deleteChatRoom(type, it.second)
        }

        adapter.itemClickRelay.subscribeWithRxLife {
            findNavController().navigate(NavGraphDirections.actionToFragmentChatRoom(it.second))
        }
    }

    private fun onToastObs(pair: Pair<ChatViewModel.ToastType, String>) {
        when (pair.first) {
            ChatViewModel.ToastType.LOAD_CHAT -> {
                binding.smartRefreshLayoutChat.finishRefresh()
                Snackbar.make(binding.rvChat, pair.second, Snackbar.LENGTH_SHORT).show()
            }
            ChatViewModel.ToastType.SWITCH_CHAT_SOUND -> {
                if (viewModel.type == type) {
                    dismissLoadingDialog()
                    Snackbar.make(binding.rvChat, pair.second, Snackbar.LENGTH_SHORT).show()
                }
            }
            ChatViewModel.ToastType.DELETE_CHAT_ROOM -> {
                if (viewModel.type == type) {
                    dismissLoadingDialog()
                    Snackbar.make(binding.rvChat, pair.second, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}