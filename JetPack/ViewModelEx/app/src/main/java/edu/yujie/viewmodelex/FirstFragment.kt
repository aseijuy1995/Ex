package edu.yujie.viewmodelex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */
class FirstFragment : Fragment() {
    private val viewModel by activityViewModels<SharedViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        view.findViewById<Button>(R.id.button).setOnClickListener {
            val value = viewModel.selected.value!!
            viewModel.setSelect(value + 1)
        }
        viewModel.selected.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.textView).text = it.toString()
        }
        return view
    }
}