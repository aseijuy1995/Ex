package edu.yujie.lohasapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.yujie.lohasapp.databinding.FragCartBinding

class CartFragment:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragCartBinding.inflate(inflater,container,false)
        return binding.root
    }
}