package tw.north27.coachingapp.ui.launch2.ask

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.ext.observe
import com.yujie.utilmodule.ext.visible
import com.yujie.utilmodule.util.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.NavGraphLaunch2Directions
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.AskListAdapter
import tw.north27.coachingapp.databinding.FragmentAskBinding
import tw.north27.coachingapp.viewModel.AskViewModel
import tw.north27.coachingapp.viewModel.PublicViewModel
import java.util.*

class AskFragment : BaseFragment<FragmentAskBinding>(R.layout.fragment_ask) {

    override val viewBind: (View) -> FragmentAskBinding
        get() = FragmentAskBinding::bind

    private val publicVM by sharedViewModel<PublicViewModel>()

    private val viewModel by viewModel<AskViewModel>()

    private val adapter = AskListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            itemToolbarNormal.apply {
                //
                ivSend.isVisible = true
                //
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
            binding.itemAskLoad.root.visible = (it is ViewState.Load)
            binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
            binding.rvAsk.isVisible = (it is ViewState.Data)
            binding.itemError.root.isVisible = (it is ViewState.Error)
            binding.itemNetwork.root.isVisible = (it is ViewState.Network)
            if (it !is ViewState.Initial && it !is ViewState.Load) binding.srlView.finishRefresh()
            when (it) {
                is ViewState.Data -> {
                    adapter.apply {
                        this.educationLevelList = publicVM.educationLevelList.value
                        this.gradeList = publicVM.gradeList.value
                        this.subjectList = publicVM.subjectList.value
                        this.unitsList = publicVM.unitList.value
                    }.submitList(it.data.toList())
                }
            }
        }

        binding.srlView.setOnRefreshListener {
            val askId = adapter.getItemId(0)
            viewModel.fetchAskRoomList(id = if (askId != -1L) askId else null)
        }

        binding.itemToolbarNormal.ivSend.clicksObserve(owner = viewLifecycleOwner) {
            val askId = adapter.getItemId(0)//取得為最新提問id，非房間id
            //
//            val ask = getAskListTest()[2].apply {
//                id = getAskListTest().maxOf(AskRoom::id) + 1
//                askType = AskType.TEXT
//                text = "測試${getAskListTest().maxOf(AskRoom::id) + 1}"
//                unreadNum = unreadNum + 1
//                sendTime = Date()
//                unitId = unitId
//                msg = "測試$id"
//            }
//            //
//            getAskListTest().apply {
//                removeAt(2)
//                add(0, ask)
//            }
            viewModel.fetchAskRoomList(id = if (askId != -1L) askId else null)
        }

        adapter.itemClickRelay.observe(viewLifecycleOwner) {
            val askRoom = it.second
            findNavController().navigate(NavGraphLaunch2Directions.actionToFragmentAskRoom(askRoom))
        }

        binding.srlView.autoRefresh()
    }
}