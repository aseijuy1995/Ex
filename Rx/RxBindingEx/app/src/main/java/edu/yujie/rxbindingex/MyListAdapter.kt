package edu.yujie.rxbindingex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import edu.yujie.rxbindingex.databinding.ItemBinding

class MyListAdapter(private val datas: List<Data>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = DataBindingUtil.inflate<ItemBinding>(inflater, R.layout.item, parent, false).apply {
            this.data = datas[position]
        }
        return binding.root
    }

    override fun getItem(position: Int): Any = datas[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = datas.size

}