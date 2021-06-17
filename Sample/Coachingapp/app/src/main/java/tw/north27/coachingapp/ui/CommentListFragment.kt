package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.yujie.utilmodule.base.BaseFragment
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.CommentListAdapter
import tw.north27.coachingapp.databinding.FragmentCommentListBinding

class CommentListFragment : BaseFragment<FragmentCommentListBinding>(R.layout.fragment_comment_list) {

    override val viewBindingFactory: (View) -> FragmentCommentListBinding
        get() = FragmentCommentListBinding::bind

    private val adapter = CommentListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.tbNormal.ivBack.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }


    }
}