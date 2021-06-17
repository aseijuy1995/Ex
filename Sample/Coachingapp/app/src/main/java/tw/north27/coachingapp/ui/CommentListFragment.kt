package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.util.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.CommentListAdapter
import tw.north27.coachingapp.databinding.FragmentCommentListBinding
import tw.north27.coachingapp.viewModel.PersonalCenterViewModel

class CommentListFragment : BaseFragment<FragmentCommentListBinding>(R.layout.fragment_comment_list) {

    override val viewBindingFactory: (View) -> FragmentCommentListBinding
        get() = FragmentCommentListBinding::bind

    private val viewModel by sharedViewModel<PersonalCenterViewModel>()

    private val commentAdapter = CommentListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvComment.adapter = commentAdapter

        binding.tbNormal.ivBack.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.tbNormal.ivFilter.clicksObserve(owner = viewLifecycleOwner) {
        }

        viewModel.commentListState.observe(viewLifecycleOwner) {
            binding.rvComment.isVisible = (it is ViewState.Data)
            when (it) {
                is ViewState.Data -> {
                    val commentList = it.data
                    commentAdapter.submitList(commentList)
                }
                //FIXME　整合處理各頁面錯誤
                is ViewState.Error, is ViewState.Network -> {
                }
            }
        }

    }
}