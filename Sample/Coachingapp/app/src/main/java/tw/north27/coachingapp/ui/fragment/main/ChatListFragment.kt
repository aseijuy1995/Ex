package tw.north27.coachingapp.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.NavGraphDirections
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.*
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.chat.ChatViewModel
import tw.north27.coachingapp.databinding.FragmentChatListBinding
import tw.north27.coachingapp.ext.*
import tw.north27.coachingapp.model.result.ChatRead
import tw.north27.coachingapp.util.ViewState

class ChatListFragment : BaseFragment(R.layout.fragment_chat_list) {

    private val binding by viewBinding<FragmentChatListBinding>(FragmentChatListBinding::bind)

    private val viewModel by sharedViewModel<ChatViewModel>()

    private val adapter: ChatListAdapter = ChatListAdapter()

    private val type: ChatReadIndex
        get() = (arguments?.getSerializable(KEY_CHAT_READ_TYPE) as ChatReadIndex)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvChat.apply {
            addItemDecoration(DividerItemDecoration(cxt, LinearLayoutManager.VERTICAL).apply {
                setDrawable(ContextCompat.getDrawable(cxt, R.drawable.shape_size_height_1_solid_gray) ?: return)
            })
            adapter = this@ChatListFragment.adapter
        }

        lifecycleScope.launch {
            viewModel.loadChatState.collect {
                if (it is ViewState.Load) {
                    binding.itemChatShinner.shimmerFrameLayoutChat.start()
                } else {
                    binding.itemChatShinner.shimmerFrameLayoutChat.stop()
                }
                binding.rvChat.isVisible = it is ViewState.Data
                if (it is ViewState.Data) {
                    val chatList = it.data
                    val list = when (type) {
                        ChatReadIndex.ALL -> chatList
                        ChatReadIndex.HAVE_READ -> chatList.filter { it.read == ChatRead.HAVE_READ }
                        ChatReadIndex.UN_READ -> chatList.filter { it.read == ChatRead.UN_READ }
                    }
                    adapter.submitList(list)
                    viewModel.listScrollToTop(true)
                }
                binding.itemEmpty.clEmpty.isVisible = it is ViewState.Empty
                binding.itemError.clError.isVisible = it is ViewState.Error
                binding.itemNetwork.clNetwork.isVisible = it is ViewState.Network
            }
        }

        binding.smartRefreshLayoutChat.setOnRefreshListener {
            viewModel.loadChat()
        }

        val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                viewModel.listScrollToTop(true)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                binding.rvChat.smoothScrollToPosition(itemCount)
            }
        }

        //接收通知滑動置頂
        binding.rvChat.adapter?.registerAdapterDataObserver(adapterDataObserver)

        viewModel.listScrollToTop.observe(viewLifecycleOwner) {
            binding.rvChat.scrollToPosition(0)
            binding.rvChat.postDelayed({
                binding.rvChat.smoothScrollToPosition(0)
            }, 500)
        }

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

        viewModel.toast.observe(viewLifecycleOwner, ::onToastObs)
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