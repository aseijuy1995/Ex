package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.north27.coachingapp.databinding.ItemSubjectLabelBinding

class SubjectLabelListAdapter : RecyclerView.Adapter<SubjectLabelListAdapter.VH>() {
    private var subjectList: List<String>? = null

    fun submitData(subjectList: List<String>) {
        this.subjectList = subjectList
        notifyDataSetChanged()
    }

    inner class VH(val binding: ItemSubjectLabelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(subject: String?) = binding.apply {
            this.subject = subject
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemSubjectLabelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val label = subjectList?.get(position)
        holder.bind(label)
    }

    override fun getItemCount(): Int {
        return subjectList?.size ?: 0
    }
}