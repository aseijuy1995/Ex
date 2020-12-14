package edu.yujie.socketex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import edu.yujie.socketex.databinding.ItemInfoBinding

class InfoListAdapter : RecyclerView.Adapter<InfoListAdapter.VH>() {
    private var infos: MutableList<String>? = null

    inner class VH(private val binding: ItemInfoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(str: String?) = binding.apply {
            msg = str
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemInfoBinding>(inflater, R.layout.item_info, parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int = infos?.size ?: 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(infos?.get(position))
    }

    fun submit(info: String) {
        if (infos == null)
            infos = mutableListOf()
        infos?.add(info)
        notifyDataSetChanged()
    }
}