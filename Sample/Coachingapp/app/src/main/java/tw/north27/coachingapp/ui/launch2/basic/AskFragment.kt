package tw.north27.coachingapp.ui.launch2.basic

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yujie.core_lib.UserPref
import com.yujie.core_lib.base.BaseFragment
import com.yujie.core_lib.ext.clicksObserve
import com.yujie.core_lib.ext.observe
import com.yujie.core_lib.ext.visible
import com.yujie.core_lib.pref.getAuth
import com.yujie.core_lib.pref.userPref
import com.yujie.core_lib.util.ViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.NavGraphLaunch2Directions
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.ask.AskListAdapter
import tw.north27.coachingapp.consts.simulation.askRoomList_Test
import tw.north27.coachingapp.databinding.FragmentAskBinding
import tw.north27.coachingapp.model.AskRoomInfo
import tw.north27.coachingapp.model.AskType
import tw.north27.coachingapp.model.response.ClientInfo
import tw.north27.coachingapp.model.response.UnitType
import tw.north27.coachingapp.model.transfer.SourceFrom
import tw.north27.coachingapp.ui.launch2.ask.EducationSelectorDialogFragment
import tw.north27.coachingapp.ui.launch2.share.LoadingDialogFragment
import tw.north27.coachingapp.ui.launch2.share.TeacherDetailDialogFragment
import tw.north27.coachingapp.viewModel.AskViewModel
import tw.north27.coachingapp.viewModel.PublicViewModel
import java.util.*

class AskFragment : BaseFragment<FragmentAskBinding>(R.layout.fragment_ask) {

    override val bind: (View) -> FragmentAskBinding
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
            rvView.apply {
                addItemDecoration(DividerItemDecoration(cxt, LinearLayoutManager.VERTICAL).apply {
                    setDrawable(ContextCompat.getDrawable(cxt, R.drawable.shape_size_height_2_solid_gray) ?: return)
                })
                adapter = this@AskFragment.adapter
            }
            lifecycleScope.launch {
                efabBtnPair.isVisible = (cxt.userPref.getAuth().first() == UserPref.Authority.STUDENT)
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
                        unitTypeList = educationData.unitTypeList
                    }
                    adapter.submitList(adapter.currentList)
                }
            }
        }

        viewModel.askRoomListState.observe(viewLifecycleOwner) {
            if (viewModel.isRefreshView.value!!) {
                binding.itemAskLoad.root.visible = (it is ViewState.Load)
                binding.itemEmpty.root.isVisible = (it is ViewState.Empty)
                binding.rvView.isVisible = (it is ViewState.Data)
                binding.itemError.root.isVisible = (it is ViewState.Error)
                binding.itemNetwork.root.isVisible = (it is ViewState.Network)
            } else {
                binding.itemAskLoad.root.visible = false
                binding.itemEmpty.root.isVisible = (it is ViewState.Empty) or (viewModel.lastAskRoomListState.value is ViewState.Empty)
                binding.rvView.isVisible = (it is ViewState.Data) or (viewModel.lastAskRoomListState.value is ViewState.Data)
                binding.itemError.root.isVisible = (it is ViewState.Error) or (viewModel.lastAskRoomListState.value is ViewState.Error)
                binding.itemNetwork.root.isVisible = (it is ViewState.Network) or (viewModel.lastAskRoomListState.value is ViewState.Network)
            }
            if (it !is ViewState.Initial && it !is ViewState.Load) binding.srlLayout.finishRefresh()
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

        binding.srlLayout.setOnRefreshListener {
            viewModel.setRefreshView(true)
            val askId = adapter.getItemId(0)
            viewModel.fetchAskRoomList(askId = askId)
        }

        binding.efabBtnPair.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigate(
                NavGraphLaunch2Directions.actionToFragmentEducationSelectorDialog(
                    sourceFrom = SourceFrom.Pair,
                    clientInfo = null
                )
            )
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
                    senderId = askRoomList_Test[1].askRoomInfo?.senderId!!,
                    receiverId = askRoomList_Test[1].askRoomInfo?.receiverId!!,
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

        //篩選器頁返回
        setFragmentResultListener(EducationSelectorDialogFragment.REQUEST_KEY_SELECTOR) { key, bundle ->
            lifecycleScope.launch {
                delay(500)
                val clientInfo: ClientInfo = bundle.getParcelable<ClientInfo>(EducationSelectorDialogFragment.KEY_SELECTOR_CLIENT)!!
                val unit: UnitType = bundle.getParcelable<UnitType>(EducationSelectorDialogFragment.KEY_SELECTOR_UNITTYPE)!!
                findNavController().navigate(
                    NavGraphLaunch2Directions.actionToFragmentTeacherDetailDialog(
                        sourceFrom = SourceFrom.Pair,
                        clientInfo = clientInfo,
                        unitType = unit
                    )
                )
            }
        }

        //老師詳細頁返回
        setFragmentResultListener(TeacherDetailDialogFragment.REQUEST_KEY_TEACHER) { key, bundle ->
            lifecycleScope.launch {
                delay(500)
                val clientInfo: ClientInfo = bundle.getParcelable<ClientInfo>(TeacherDetailDialogFragment.KEY_TEACHER_CLIENT)!!
                val unitType: UnitType = bundle.getParcelable<UnitType>(TeacherDetailDialogFragment.KEY_TEACHER_UNITTYPE)!!
                val msg: String = bundle.getString(TeacherDetailDialogFragment.KEY_TEACHER_MSG)!!
                findNavController().navigate(
                    NavGraphLaunch2Directions.actionToFragmentSetupAskRoomDialog(
                        clientInfo = clientInfo,
                        unitType = unitType,
                        msg = msg
                    )
                )
            }
        }

        binding.srlLayout.autoRefresh()
    }
}