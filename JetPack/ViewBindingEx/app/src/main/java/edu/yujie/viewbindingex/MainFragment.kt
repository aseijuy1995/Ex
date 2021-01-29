package edu.yujie.viewbindingex

import android.os.Bundle
import android.view.View
import edu.yujie.socketex.base.finish.base.BaseViewBindingFragment
import edu.yujie.viewbindingex.databinding.FragmentMainBinding

/**
 * @author YuJie on 2020/10/17
 * @describe 說明
 * @param 參數
 */
class MainFragment : BaseViewBindingFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private var count = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvView.setOnClickListener {
            binding.tvView.text = "Fragment: ${count++}"
        }
    }
}