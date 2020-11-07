package edu.yujie.navigationex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.yujie.navigationex.databinding.FragmentThiredBinding

class ThirdFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentThiredBinding.inflate(inflater,container,false)

        binding.tvText.setOnClickListener {
            findNavController().navigate(R.id.action_to_fragment_first)

        }
        return binding.root
    }
}