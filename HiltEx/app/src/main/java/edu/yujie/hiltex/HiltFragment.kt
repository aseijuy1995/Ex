package edu.yujie.hiltex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HiltFragment : Fragment() {
    @Inject
    lateinit var hiltSimple: HiltSimple

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hilt, container,false)
        val textView = view.findViewById<TextView>(R.id.textView)
        textView.text=hiltSimple.doSomething()
        return view
    }
}