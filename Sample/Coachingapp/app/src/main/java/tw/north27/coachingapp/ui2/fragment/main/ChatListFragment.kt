package tw.north27.coachingapp.ui2.fragment.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.google.android.material.snackbar.Snackbar
import com.yujie.basemodule.viewBinding
import com.yujie.utilmodule.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.*
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.chat.ChatViewModel
import tw.north27.coachingapp.databinding.FragmentChatListBinding
import tw.north27.coachingapp.ext2.errorAlert
import tw.north27.coachingapp.ext2.networkAlert
import tw.north27.coachingapp.ext2.startAlphaBreatheAnim
import tw.north27.coachingapp.ext2.stop
import tw.north27.coachingapp.model.result.ChatRead

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

        viewModel.chatState.observe(viewLifecycleOwner) {
            if (it is ViewState.Load) {
                binding.itemChatShinner.shimmerFrameLayoutChat.startAlphaBreatheAnim()
            } else {
                binding.itemChatShinner.shimmerFrameLayoutChat.stop()
                binding.smartRefreshLayoutChat.finishRefresh()
            }
            binding.rvChat.isVisible = (it is ViewState.Data)
            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
            binding.itemError.root.isVisible = (it is ViewState.Error)
            binding.itemNetwork.root.isVisible = (it is ViewState.Network)

            when (it) {
                is ViewState.Initial -> {
                }
                is ViewState.Load -> {
                }
                is ViewState.Empty -> {
                }
                is ViewState.Data -> {
                    val chatList = it.data
                    val list = when (type) {
                        ChatReadIndex.ALL -> chatList
                        ChatReadIndex.HAVE_READ -> chatList.filter { it.read == ChatRead.HAVE_READ }
                        ChatReadIndex.UN_READ -> chatList.filter { it.read == ChatRead.UN_READ }
                    }
                    adapter.submitList(list)
                    viewModel.listScrollToTop(true)
                }
                is ViewState.Error -> {
                    act.errorAlert()

                }
                is ViewState.Network -> {
                    act.networkAlert()
                }
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
            viewModel.switchChatSound(type, it.second)
        }

        viewModel.chatSoundState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Initial -> {
                }
                is ViewState.Load -> {
                    showLoadingDialog()
                }
                is ViewState.Empty -> {
                    dismissLoadingDialog()
                }
                is ViewState.Data -> {
                    dismissLoadingDialog()
                    Snackbar.make(binding.rvChat, if (it.data.isSound) getString(R.string.open_sound_notify) else getString(R.string.close_sound_notify), Snackbar.LENGTH_SHORT).show()
                }
                is ViewState.Error -> {
                    dismissLoadingDialog()
                    act.errorAlert()
                }
                is ViewState.Network -> {
                    dismissLoadingDialog()
                    act.networkAlert()
                }
            }
        }

        adapter.deleteClickRelay.subscribeWithRxLife {
            showLoadingDialog()
            viewModel.deleteChatRoom(type, it.second)
        }

        viewModel.chatDeleteState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Initial -> {
                }
                is ViewState.Load -> {
                    showLoadingDialog()
                }
                is ViewState.Empty -> {
                    dismissLoadingDialog()
                }
                is ViewState.Data -> {
                    dismissLoadingDialog()
                    Snackbar.make(binding.rvChat, getString(R.string.deleted), Snackbar.LENGTH_SHORT).show()
                }
                is ViewState.Error -> {
                    dismissLoadingDialog()
                    act.errorAlert()
                }
                is ViewState.Network -> {
                    dismissLoadingDialog()
                    act.networkAlert()
                }
            }
        }

        adapter.itemClickRelay.subscribeWithRxLife {
//            findNavController().navigate(NavGraphDirections.actionToFragmentChatRoom(it.second))
        }

    }
}