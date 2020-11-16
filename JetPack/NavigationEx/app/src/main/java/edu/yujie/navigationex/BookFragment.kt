package edu.yujie.navigationex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.yujie.navigationex.databinding.FragBookBinding

/**
 * @author YuJie on 2020/11/13
 * @describe 說明
 * @param 參數
 */
class BookFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragBookBinding.inflate(inflater, container, false)
        return binding.root
    }
}