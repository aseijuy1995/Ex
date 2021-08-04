package tw.north27.coachingapp.adapter.info

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import tw.north27.coachingapp.ui.launch2.info.ExplainDetailFragment

class ExplainPagerAdapter(fragAct: FragmentActivity) : FragmentStateAdapter(fragAct) {

    val pageFragmentList = mapOf<Int, Fragment>(
        ExplainDetailFragment.COACHING to ExplainDetailFragment().apply {
            arguments = bundleOf(ExplainDetailFragment.KEY_EXPLAIN to ExplainDetailFragment.COACHING)
        },
        ExplainDetailFragment.ASK to ExplainDetailFragment().apply {
            arguments = bundleOf(ExplainDetailFragment.KEY_EXPLAIN to ExplainDetailFragment.ASK)
        },
        ExplainDetailFragment.STUDY to ExplainDetailFragment().apply {
            arguments = bundleOf(ExplainDetailFragment.KEY_EXPLAIN to ExplainDetailFragment.STUDY)
        },
        ExplainDetailFragment.NOTICE to ExplainDetailFragment().apply {
            arguments = bundleOf(ExplainDetailFragment.KEY_EXPLAIN to ExplainDetailFragment.NOTICE)
        },
        ExplainDetailFragment.PERSONAL to ExplainDetailFragment().apply {
            arguments = bundleOf(ExplainDetailFragment.KEY_EXPLAIN to ExplainDetailFragment.PERSONAL)
        },
    )

    override fun getItemCount(): Int {
        return pageFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return pageFragmentList[position] ?: throw IndexOutOfBoundsException()
    }

}