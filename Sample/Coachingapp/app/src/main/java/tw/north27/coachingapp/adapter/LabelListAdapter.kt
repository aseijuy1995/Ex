package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.north27.coachingapp.databinding.ItemLabelBinding

class LabelListAdapter : RecyclerView.Adapter<LabelListAdapter.VH>() {
    private var labelList: List<String>? = null

    fun submitData(labelList: List<String>? = null) {
        this.labelList = labelList
        notifyDataSetChanged()
    }

    inner class VH(val binding: ItemLabelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(label: String?) = binding.apply {
            this.label = label
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemLabelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val label = labelList?.get(position)
        holder.bind(label)
    }

    override fun getItemCount(): Int {
        return labelList?.size ?: 0
    }
}