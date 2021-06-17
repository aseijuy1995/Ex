package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tw.north27.coachingapp.databinding.ItemCommentBinding
import tw.north27.coachingapp.model.CommentInfo

class CommentListAdapter : ListAdapter<CommentInfo, CommentListAdapter.VH>(object : DiffUtil.ItemCallback<CommentInfo>() {
    override fun areItemsTheSame(oldItem: CommentInfo, newItem: CommentInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CommentInfo, newItem: CommentInfo): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
) {

    inner class VH(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(commentInfo: CommentInfo) = binding.apply {
            this.commentInfo = commentInfo
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