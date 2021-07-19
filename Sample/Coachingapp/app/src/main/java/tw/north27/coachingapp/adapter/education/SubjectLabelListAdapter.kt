package tw.north27.coachingapp.adapter.education

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.north27.coachingapp.databinding.ItemSubjectLabelBinding
import tw.north27.coachingapp.model.response.Subject

class SubjectLabelListAdapter : RecyclerView.Adapter<SubjectLabelListAdapter.VH>() {
    private var subjectIdList: List<Long>? = null

    var subjectList: List<Subject>? = null

    fun submitData(subjectList: List<Long>?) {
        this.subjectIdList = subjectList
        notifyDataSetChanged()
    }

    inner class VH(val binding: ItemSubjectLabelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(subjectId: Long?) = binding.apply {
            this.text = subjectList?.find { it.id == subjectId }?.name
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSubjectLabelBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val subjectId = subjectIdList?.get(position)
        holder.bind(subjectId)
    }

    override fun getItemCount(): Int {
        return subjectIdList?.size ?: 0
    }
}