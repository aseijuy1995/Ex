package tw.north27.coachingapp.ui.launch2.info

import android.os.Bundle
import android.view.View
import com.yujie.core_lib.adapter.bindImg
import com.yujie.core_lib.base.BaseFragment
import com.yujie.core_lib.util.logD
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentExplainDetailBinding

class ExplainDetailFragment : BaseFragment<FragmentExplainDetailBinding>(R.layout.fragment_explain_detail) {

    override val viewBind: (View) -> FragmentExplainDetailBinding
        get() = FragmentExplainDetailBinding::bind

    private val type: Int
        get() = arguments?.getInt(KEY_EXPLAIN, 0) ?: 0
//        ?: {
//        Toast.makeText(cxt, "not find explain", Toast.LENGTH_SHORT).show()
//        findNavController().navigateUp()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logD("type = $type")
        if (type !is Int) return
        when (type) {
            COACHING -> {
                binding.apply {
                    tvTitle.text = getString(R.string.coaching)
                    ivView.bindImg(resId = R.drawable.ic_coaching_layout, roundingRadius = 10)
                }
            }
            ASK -> {
                binding.apply {
                    tvTitle.text = getString(R.string.ask)
                    ivView.bindImg(resId = R.drawable.ic_ask_layout, roundingRadius = 10)
                }
            }
            STUDY -> {
                binding.apply {
                    tvTitle.text = getString(R.string.study)
                    ivView.bindImg(resId = R.drawable.ic_study_layout, roundingRadius = 10)
                }
            }
            NOTICE -> {
                binding.apply {
                    tvTitle.text = getString(R.string.notice)
                    ivView.bindImg(resId = R.drawable.ic_notice_layout, roundingRadius = 10)
                }
            }
            PERSONAL -> {
                binding.apply {
                    tvTitle.text = getString(R.string.personal)
                    ivView.bindImg(resId = R.drawable.ic_personal_layout, roundingRadius = 10)
                }
            }
        }
    }

    companion object {
        const val KEY_EXPLAIN = "KEY_EXPLAIN"

        const val COACHING = 0

        const val ASK = 1

        const val STUDY = 2

        const val NOTICE = 3

        const val PERSONAL = 4
    }

}