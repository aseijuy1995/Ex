package edu.yujie.navigationex

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author YuJie on 2020/11/14
 * @describe 說明
 * @param 參數
 */
class DemoCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 10

    override fun createFragment(position: Int): Fragment {
        val fragment = DemoObjectFragment()
        fragment.arguments = bundleOf("ARG_OBJECT" to position + 1)
        return fragment
    }
}