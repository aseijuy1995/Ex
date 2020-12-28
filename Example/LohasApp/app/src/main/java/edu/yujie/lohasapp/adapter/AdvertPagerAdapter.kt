package edu.yujie.lohasapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import edu.yujie.lohasapp.AdvertBean
import edu.yujie.lohasapp.ui.AdvertFragment

class AdvertPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private var mAdverts: List<AdvertBean>? = null

    override fun getItemCount(): Int = mAdverts?.size ?: 0

    override fun createFragment(position: Int): Fragment {
        val fragment = AdvertFragment()
        mAdverts?.let {
            val data = it[position]
            fragment.submit(data)
        }
        return fragment
    }

    fun submit(adverts: List<AdvertBean>) {
        mAdverts = adverts
        notifyDataSetChanged()
    }
}