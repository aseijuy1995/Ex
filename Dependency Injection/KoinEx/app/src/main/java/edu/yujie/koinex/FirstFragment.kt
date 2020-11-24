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

class FirstFragment : Fragment() {
    private val viewModel: DetailViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        viewModel.mData.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.textView).text = it
        }
        view.findViewById<Button>(R.id.button).setOnClickListener {
            val value = Random().nextInt(100).toString()
            viewModel.setData("Random value = $value")
        }
        return view
    }
}