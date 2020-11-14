package edu.yujie.navigationex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.yujie.navigationex.databinding.FragThirdBinding

/**
 * @author YuJie on 2020/11/13
 * @describe 說明
 * @param 參數
 */
class ThirdFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragThirdBinding.inflate(inflater, container, false)
        binding.textView.setOnClickListener {
            findNavController().navigate(ThirdFragmentDirections.actionFragThirdToFragFourth())
        }
        /**
         * 測試Deep Link
         * adb shell am start -a android.intent.action.VIEW -d "https://www.yujie1995.com"
         * */
        arguments?.let {
            if (it.containsKey("value")) {
                val value = it.getString("value")
                println("ThirdFragment :value = $value")
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.containsKey("args")) {
                val args = it.getInt("args")
                println("${javaClass.simpleName}:args = $args")
            }
        }
    }
}