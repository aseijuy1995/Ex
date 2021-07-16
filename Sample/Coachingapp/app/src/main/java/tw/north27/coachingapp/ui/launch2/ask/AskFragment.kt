package tw.north27.coachingapp.ui.launch2.ask

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yujie.core_lib.base.BaseFragment
import com.yujie.core_lib.ext.clicksObserve
import com.yujie.core_lib.ext.observe
import com.yujie.core_lib.ext.visible
import com.yujie.core_lib.util.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.NavGraphLaunch2Directions
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.AskListAdapter
import tw.north27.coachingapp.consts.simulation.askRoomList_Test
import tw.north27.coachingapp.databinding.FragmentAskBinding
import tw.north27.coachingapp.model.AskRoomInfo
import tw.north27.coachingapp.model.AskType
import tw.north27.coachingapp.ui.LoadingDialogFragment
import tw.north27.coachingapp.viewModel.AskViewModel
import tw.north27.coachingapp.viewModel.PublicViewModel
import java.util.*

class AskFragment : BaseFragment<FragmentAskBinding>(R.layout.fragment_ask) {

    override val viewBind: (View) -> FragmentAskBinding
        get() = FragmentAskBinding::bind

    private val publicVM by sharedViewModel<PublicViewModel>()

    private val viewModel by viewModel<AskViewModel>()

    private lateinit var adapter: AskListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AskListAdapter(cxt = cxt)
        binding.apply {
            itemToolbarNormal.apply {
                ivSend.isVisible = true
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

        publicVM.educationState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Data -> {
                    val educationData = it.data
                    adapter.apply {
                        educationLevelList = educationData.educationLevelList
                        gradeList = educationData.gradeList
                        subjectList = educationData.subjectList
                        unitsList = educationData.unitList
                    }
                    adapter.submitList(adapter.currentList)
                }
            }
        }

        viewModel.askRoomListState.observe(viewLifecycleOwner) {
            if (viewModel.isRefreshView.value!!) {
                binding.itemAskLoad.root.visible = (it is ViewState.Load)
                binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
                binding.rvAsk.isVisible = (it is ViewState.Data)
                binding.itemError.root.isVisible = (it is ViewState.Error)
                binding.itemNetwork.root.isVisible = (it is ViewState.Network)
            } else {
                binding.itemAskLoad.root.visible = false
                binding.itemEmpty.root.isVisible = (it is ViewState.Empty) or (viewModel.lastAskRoomListState.value is ViewState.Empty)
                binding.rvAsk.isVisible = (it is ViewState.Data) or (viewModel.lastAskRoomListState.value is ViewState.Data)
                binding.itemError.root.isVisible = (it is ViewState.Error) or (viewModel.lastAskRoomListState.value is ViewState.Error)
                binding.itemNetwork.root.isVisible = (it is ViewState.Network) or (viewModel.lastAskRoomListState.value is ViewState.Network)
            }
            if (it !is ViewState.Initial && it !is ViewState.Load) binding.srlView.finishRefresh()
            when (it) {
                is ViewState.Empty -> {
                    adapter.submitList(emptyList())
                }
                is ViewState.Data -> {
                    val askRoomList = it.data
                    adapter.submitList(askRoomList)
                }
            }
        }

        adapter.itemClickRelay.observe(viewLifecycleOwner) {
            val askRoom = it.second
            findNavController().navigate(NavGraphLaunch2Directions.actionToFragmentAskRoom(askRoom))
        }

        adapter.pushClickRelay.observe(viewLifecycleOwner) {
            val askRoom = it.second
            viewModel.updateAskRoomPush(roomId = askRoom.id, state = !askRoom.isPush)
        }

        adapter.soundClickRelay.observe(viewLifecycleOwner) {
            val askRoom = it.second
            viewModel.updateAskRoomSound(roomId = askRoom.id, state = !askRoom.isSound)
        }

        viewModel.pushState.observe(viewLifecycleOwner) {
            if (it is ViewState.Load) LoadingDialogFragment.show(parentFragmentManager)
            if (it !is ViewState.Initial && it !is ViewState.Load) LoadingDialogFragment.dismiss()
            when (it) {
                is ViewState.Data -> {
                    val pushResponse = it.data
                    Toast.makeText(cxt, pushResponse.msg, Toast.LENGTH_SHORT).show()
                    if (pushResponse.isSuccess) adapter.submitPushState(roomId = pushResponse.roomId!!, state = pushResponse.isState!!)
                }
            }
        }

        viewModel.soundState.observe(viewLifecycleOwner) {
            if (it is ViewState.Load) LoadingDialogFragment.show(parentFragmentManager)
            if (it !is ViewState.Initial && it !is ViewState.Load) LoadingDialogFragment.dismiss()
            when (it) {
                is ViewState.Data -> {
                    val soundResponse = it.data
                    Toast.makeText(cxt, soundResponse.msg, Toast.LENGTH_SHORT).show()
                    if (soundResponse.isSuccess) adapter.submitSoundState(roomId = soundResponse.roomId!!, state = soundResponse.isState!!)
                }
            }
        }

        binding.srlView.setOnRefreshListener {
            viewModel.setRefreshView(true)
            val askId = adapter.getItemId(0)
            viewModel.fetchAskRoomList(askId = askId)
        }

        binding.efabPair.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(AskFragmentDirections.actionFragmentAskToFragmentEducationSelectorDialog())
        }

        /**
         * 測試用
         * */
        binding.itemToolbarNormal.ivSend.clicksObserve(owner = viewLifecycleOwner) {
            viewModel.setRefreshView(false)
            val askId = adapter.getItemId(0)//取得為最新提問id，非房間id
            val ask = askRoomList_Test[1].copy(
                unreadNum = (askRoomList_Test[1].unreadNum + 1),
                askRoomInfo = AskRoomInfo(
                    id = id + 100L,
                    senderId = askRoomList_Test[1].askRoomInfo.senderId,
                    receiverId = askRoomList_Test[1].askRoomInfo.receiverId,
                    askType = AskType.TEXT,
                    text = "那數線模式或平衡模式的區別在哪呢?",
                    isRead = false,
                    sendTime = Date()
                )
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) askRoomList_Test.removeIf { it.id == ask.id }
            askRoomList_Test.add(0, ask)
            viewModel.fetchAskRoomList(askId = askId)
        }

        binding.srlView.autoRefresh()
    }
}