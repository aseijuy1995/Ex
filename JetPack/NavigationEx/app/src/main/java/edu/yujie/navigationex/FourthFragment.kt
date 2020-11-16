package edu.yujie.navigationex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import edu.yujie.navigationex.databinding.FragFourthBinding

/**
 * @author YuJie on 2020/11/13
 * @describe 說明
 * @param 參數
 */
class FourthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragFourthBinding.inflate(inflater, container, false)

        binding.textView.setOnClickListener {
//            findNavController().navigate(FourthFragmentDirections.actionFragFourthToNavGraph())
            findNavController().navigate(R.id.action_pop_out_nav_graph)
        }

        binding.pager.adapter = DemoCollectionAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab: TabLayout.Tab, i: Int ->
            tab.text = getTabTitle(i)
            tab.setIcon(getTabIcon(i))
        }.attach()
        return binding.root
    }

    private fun getTabTitle(position: Int): String? =
        when (position) {
            0 -> "First"
            1 -> "Second"
            2 -> "Third"
            3 -> "Fourth"
            4 -> "Fifth"
            else -> "Other"
        }

    private fun getTabIcon(position: Int): Int = R.drawable.ic_jetpack
}