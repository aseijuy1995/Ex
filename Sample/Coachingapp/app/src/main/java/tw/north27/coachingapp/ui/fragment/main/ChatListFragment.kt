package tw.north27.coachingapp.ui.fragment.main

import android.os.Bundle
import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.ChatListAdapter
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.chat.ChatListViewModel
import tw.north27.coachingapp.databinding.FragmentChatListBinding
import tw.north27.coachingapp.ext.viewBinding

class ChatListFragment : BaseFragment(R.layout.fragment_chat_list) {

    private val binding by viewBinding<FragmentChatListBinding>(FragmentChatListBinding::bind)

    private val viewModel by viewModel<ChatListViewModel>()

    private val adapter = ChatListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvChat.adapter = adapter

        viewModel.loadChat().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }
}