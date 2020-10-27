package edu.yujie.viewbindingex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.yujie.viewbindingex.databinding.FragmentMainBinding

/**
 * @author YuJie on 2020/10/17
 * @describe 說明
 * @param 參數
 */
class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }
}