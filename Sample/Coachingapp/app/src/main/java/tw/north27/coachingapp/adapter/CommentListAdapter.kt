package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tw.north27.coachingapp.databinding.ItemCommentBinding
import tw.north27.coachingapp.model.CommentInfo
import tw.north27.coachingapp.model.response.EducationLevel
import tw.north27.coachingapp.model.response.Grade
import tw.north27.coachingapp.model.response.Subject
import tw.north27.coachingapp.model.response.UnitType

class CommentListAdapter : ListAdapter<CommentInfo, CommentListAdapter.VH>(object : DiffUtil.ItemCallback<CommentInfo>() {
    override fun areItemsTheSame(oldItem: CommentInfo, newItem: CommentInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CommentInfo, newItem: CommentInfo): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}) {

    var educationLevelList: List<EducationLevel>? = null

    var gradeList: List<Grade>? = null

    var subjectList: List<Subject>? = null

    var unitTypeList: List<UnitType>? = null

    inner class VH(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(commentInfo: CommentInfo) = binding.apply {
            this.commentInfo = commentInfo
            this.education = educationLevelList?.find { it.id == commentInfo.educationId }
            this.grade = gradeList?.find { it.id == commentInfo.gradeId }
            this.subject = subjectList?.find { it.id == commentInfo.subjectId }
            this.unitType = unitTypeList?.find { it.id == commentInfo.unitId }
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val commentInfo = getItem(position)
        holder.bind(commentInfo)
    }

}