package edu.yujie.koinex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class SecondFragment : Fragment() {
    private val viewModel: DetailViewModel by sharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        viewModel.mData.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.textView).text = it
        }
        return view
    }
}