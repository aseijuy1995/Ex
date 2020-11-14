package edu.yujie.navigationex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.yujie.navigationex.databinding.FragKotlinDsl2Binding

/**
 * @author YuJie on 2020/11/14
 * @describe 說明
 * @param 參數
 */
class KotlinDsl2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragKotlinDsl2Binding.inflate(inflater, container, false)

        binding.textView.setOnClickListener {
            findNavController().navigate(nav_graph.kotlin_dsl2.action.frag_kotlin_dsl2_to_frag_kotlin_dsl)
            Toast.makeText(requireContext(), "Enter KotlinDslFragment", Toast.LENGTH_SHORT).show()
        }

        binding.btnMain.setOnClickListener {
            findNavController().navigate(nav_graph.kotlin_dsl2.action.frag_kotlin_dsl2_to_act_main)
            Toast.makeText(requireContext(), "Enter MainActivity", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}