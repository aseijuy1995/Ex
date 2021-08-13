package tw.north27.coachingapp.ui.launch2.info

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.yujie.core_lib.base.BaseFragment
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.info.ExplainPagerAdapter
import tw.north27.coachingapp.databinding.FragmentExplainBinding

class ExplainFragment : BaseFragment<FragmentExplainBinding>(R.layout.fragment_explain) {

    override val bind: (View) -> FragmentExplainBinding
        get() = FragmentExplainBinding::bind

    private val adapter: ExplainPagerAdapter by lazy { ExplainPagerAdapter(act) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpPager.apply {
            adapter = this@ExplainFragment.adapter
            offscreenPageLimit = this@ExplainFragment.adapter.pageFragmentList.size
//            isUserInputEnabled

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        ExplainDetailFragment.COACHING,
                        ExplainDetailFragment.ASK,
                        ExplainDetailFragment.STUDY,
                        ExplainDetailFragment.NOTICE -> {
                            binding.apply {
                                tvSkip.isVisible = true
                                tvFinish.isVisible = false
                            }
                        }
                        ExplainDetailFragment.PERSONAL -> {
                            binding.apply {
                                tvSkip.isVisible = false
                                tvFinish.isVisible = true
                            }
                        }
                    }
                }
            })
        }

//        binding.btnSkip.clicksObserve(owner = viewLifecycleOwner) {
//            findNavController().navigateUp()
//        }
//
//        binding.btnFinish.clicksObserve(owner = viewLifecycleOwner) {
//            findNavController().navigateUp()
//        }

    }

}