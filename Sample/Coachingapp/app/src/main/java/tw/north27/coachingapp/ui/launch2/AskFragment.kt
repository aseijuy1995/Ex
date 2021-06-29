package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.ext.observe
import com.yujie.utilmodule.ext.visible
import com.yujie.utilmodule.util.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.NavGraphLaunch2Directions
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.AskListAdapter
import tw.north27.coachingapp.databinding.FragmentAskBinding
import tw.north27.coachingapp.viewModel.AskViewModel

class AskFragment : BaseFragment<FragmentAskBinding>(R.layout.fragment_ask) {

    override val viewBind: (View) -> FragmentAskBinding
        get() = FragmentAskBinding::bind

    private val launch2Act: Launch2Activity
        get() = act as Launch2Activity

    private val viewModel by viewModel<AskViewModel>()

    private val adapter = AskListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            itemToolbarNormal.apply {
                ivBack.isVisible = false
                tvTitle.text = getString(R.string.ask_title)
            }
            rvAsk.apply {
                addItemDecoration(DividerItemDecoration(cxt, LinearLayoutManager.VERTICAL).apply {
                    setDrawable(ContextCompat.getDrawable(cxt, R.drawable.shape_size_height_2_solid_gray) ?: return)
                })
                adapter = this@AskFragment.adapter
            }
        }

        viewModel.askInfoListState.observe(viewLifecycleOwner) {
            binding.itemAskLoad.sflView.visible = (it is ViewState.Load)
            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
            binding.rvAsk.isVisible = (it is ViewState.Data)
            binding.itemError.root.isVisible = (it is ViewState.Error)
            binding.itemNetwork.root.isVisible = (it is ViewState.Network)
            if (it !is ViewState.Initial && it !is ViewState.Load)
                binding.srlView.finishRefresh()
            when (it) {
                is ViewState.Data -> {
                    adapter.submitList(it.data)
                }
            }
        }


        binding.srlView.setOnRefreshListener {
            viewModel.fetchAskList(
//                index = 0,
//                num = 20
            )
        }
//
//        val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
//            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
//                super.onItemRangeInserted(positionStart, itemCount)
//                viewModel.listScrollToTop(true)
//            }
//
//            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
//                super.onItemRangeRemoved(positionStart, itemCount)
//                binding.rvChat.smoothScrollToPosition(itemCount)
//            }
//        }
//
//        //接收通知滑動置頂
//        binding.rvChat.adapter?.registerAdapterDataObserver(adapterDataObserver)
//
//        viewModel.listScrollToTop.observe(viewLifecycleOwner) {
//            binding.rvChat.scrollToPosition(0)
//            binding.rvChat.postDelayed({
//                binding.rvChat.smoothScrollToPosition(0)
//            }, 500)
//        }
//
//        adapter.soundClickRelay.observe(viewLifecycleOwner) {
//            viewModel.switchChatSound(type, it.second)
//        }
//


        adapter.itemClickRelay.observe(viewLifecycleOwner) {
            //            it.second
            findNavController().navigate(NavGraphLaunch2Directions.actionToFragmentAskRoom())
        }

        binding.srlView.autoRefresh()
    }
}