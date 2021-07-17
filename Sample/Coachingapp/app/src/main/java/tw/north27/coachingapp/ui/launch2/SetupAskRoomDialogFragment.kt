package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.yujie.core_lib.adapter.bindImg
import com.yujie.core_lib.base.BaseDialogFragment
import com.yujie.core_lib.ext.clicksObserve
import com.yujie.core_lib.util.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.NavGraphLaunch2Directions
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentSetupAskRoomDialogBinding
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.response.Units
import tw.north27.coachingapp.ui.LoadingDialogFragment
import tw.north27.coachingapp.viewModel.SetupAskRoomViewModel

class SetupAskRoomDialogFragment : BaseDialogFragment<FragmentSetupAskRoomDialogBinding>(R.layout.fragment_setup_ask_room_dialog) {

    override val viewBind: (View) -> FragmentSetupAskRoomDialogBinding
        get() = FragmentSetupAskRoomDialogBinding::bind

    private val viewModel by viewModel<SetupAskRoomViewModel>()

    private val clientInfo: ClientInfo
        get() = arguments?.getParcelable<ClientInfo>("clientInfo")!!

    private val unit: Units
        get() = arguments?.getParcelable<Units>("unit")!!

    private val msg: String
        get() = arguments?.getString("msg")!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val bgUrl = clientInfo.bgUrl
            if (bgUrl.isNotEmpty()) ivBg.bindImg(url = clientInfo.bgUrl)
            val avatarUrl = clientInfo.avatarUrl
            if (avatarUrl.isNotEmpty()) ivAvatar.bindImg(url = clientInfo.avatarUrl, placeRes = R.drawable.ic_baseline_account_box_24_gray, roundingRadius = 10)
            tvName.text = clientInfo.name
            tvText.text = msg
            tvUnit.text = unit.name
        }

        viewModel.askRoomState.observe(viewLifecycleOwner) {
            if (it is ViewState.Load) LoadingDialogFragment.show(parentFragmentManager)
            if (it !is ViewState.Initial && it !is ViewState.Load) LoadingDialogFragment.dismiss()
            when (it) {
                is ViewState.Data -> {
                    val askRoom = it.data
                    findNavController().navigate(NavGraphLaunch2Directions.actionToFragmentAskRoom(askRoom))
                }
            }
        }

        binding.ivClear.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.btnEnter.clicksObserve(owner = viewLifecycleOwner) {
            viewModel.setupAskRoom(
                otherClientId = clientInfo.id,
                unitId = unit.id
            )
        }
    }

}