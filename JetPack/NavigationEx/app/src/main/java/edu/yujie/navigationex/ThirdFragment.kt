package edu.yujie.navigationex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
<<<<<<< HEAD
import edu.yujie.navigationex.databinding.FragThirdBinding

/**
 * @author YuJie on 2020/11/13
 * @describe 說明
 * @param 參數
 */
class ThirdFragment : Fragment() {
=======
import edu.yujie.navigationex.databinding.FragmentThiredBinding

class ThirdFragment:Fragment() {
>>>>>>> 5353c08b8648ab256f1ef3dde1fdfbf3127f072e

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
<<<<<<< HEAD
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
=======
        val binding = FragmentThiredBinding.inflate(inflater,container,false)

        binding.tvText.setOnClickListener {
            findNavController().navigate(R.id.action_to_fragment_first)

        }
        return binding.root
>>>>>>> 5353c08b8648ab256f1ef3dde1fdfbf3127f072e
    }
}