package edu.yujie.viewbindingex

import androidx.fragment.app.Fragment
import edu.yujie.viewbindingex.databinding.FragmentMain2Binding

/**
 * @author YuJie on 2020/10/17
 * @describe 說明
 * @param 參數
 */

class Main2Fragment : Fragment(R.layout.fragment_main2) {
    private val binding: FragmentMain2Binding by viewBinding {
        FragmentMain2Binding.bind(requireView())
    }
}