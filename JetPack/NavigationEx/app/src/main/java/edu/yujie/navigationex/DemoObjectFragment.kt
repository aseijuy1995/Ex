package edu.yujie.navigationex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.yujie.navigationex.databinding.FragDemoObjectBinding

/**
 * @author YuJie on 2020/11/14
 * @describe 說明
 * @param 參數
 */
class DemoObjectFragment : Fragment() {
    private lateinit var binding: FragDemoObjectBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragDemoObjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf {
            it.containsKey("ARG_OBJECT").apply {
                binding.textView.append(it.getInt("ARG_OBJECT").toString())
            }
        }
    }
}