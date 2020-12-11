package edu.yujie.rxbindingex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import edu.yujie.rxbindingex.databinding.ItemBinding

class MyRvListAdapter(private val datas: List<Data>) : RecyclerView.Adapter<MyRvListAdapter.VH>() {

    inner class VH(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) = binding.apply {
            this.data = data
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemBinding>(inflater, R.layout.item, parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(datas[position])
    }
}