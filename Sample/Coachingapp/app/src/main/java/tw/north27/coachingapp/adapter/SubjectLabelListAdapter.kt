package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import tw.north27.coachingapp.databinding.ItemSubjectLabelBinding
import tw.north27.coachingapp.model.response.Subject
import tw.north27.coachingapp.ui.launch2.Launch2Activity

class SubjectLabelListAdapter(private val act: AppCompatActivity) : RecyclerView.Adapter<SubjectLabelListAdapter.VH>() {
    private var subjectIdList: List<Long>? = null

    fun submitData(subjectList: List<Long>?) {
        this.subjectIdList = subjectList
        notifyDataSetChanged()
    }

    inner class VH(val binding: ItemSubjectLabelBinding, val subjectList: List<Subject>?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(subjectId: Long?) = binding.apply {
            this.text = subjectList?.find { it.id == subjectId }?.name
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemSubjectLabelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val subjectList = (act as Launch2Activity).publicVM.subjectList.value
        return VH(binding, subjectList)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val subjectId = subjectIdList?.get(position)
        holder.bind(subjectId)
    }

    override fun getItemCount(): Int {
        return subjectIdList?.size ?: 0
    }
}