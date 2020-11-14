package edu.yujie.navigationex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.yujie.navigationex.databinding.FragKotlinDslBinding

/**
 * @author YuJie on 2020/11/14
 * @describe 說明
 * @param 參數
 */
class KotlinDslFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragKotlinDslBinding.inflate(inflater, container, false)

        binding.textView.setOnClickListener {
            findNavController().navigate(nav_graph.kotlin_dsl.action.frag_kotlin_dsl_to_frag_kotlin_dsl2)
            Toast.makeText(requireContext(), "Enter KotlinDsl2Fragment", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}