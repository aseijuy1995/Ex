package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.yujie.core_lib.base.BaseFragment
import com.yujie.core_lib.ext.clicksObserve
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.CommonProblemListAdapter
import tw.north27.coachingapp.databinding.FragmentCommonProblemBinding
import tw.north27.coachingapp.ui.launch2.basic.Launch2Activity

class CommonProblemFragment : BaseFragment<FragmentCommonProblemBinding>(R.layout.fragment_common_problem) {

    override val bind: (View) -> FragmentCommonProblemBinding
        get() = FragmentCommonProblemBinding::bind

    private val launch2Act: Launch2Activity
        get() = act as Launch2Activity

    private lateinit var adapter: CommonProblemListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.itemToolbarNormal.tvTitle.text = getString(R.string.common_problem)
        adapter = CommonProblemListAdapter(launch2Act.publicVM.commonProblemList.value ?: emptyList())
        binding.elvView.apply {
            setAdapter(this@CommonProblemFragment.adapter)
            setOnGroupClickListener { parent, v, groupPosition, id ->
                if (binding.elvView.isGroupExpanded(groupPosition))
                    binding.elvView.collapseGroupWithAnimation(groupPosition)
                else
                    binding.elvView.expandGroupWithAnimation(groupPosition)
                true
            }
        }

        binding.itemToolbarNormal.ivBack.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }
    }

}