package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.PublishRelay
import tw.north27.coachingapp.databinding.ItemTeacherBinding
import tw.north27.coachingapp.model.UserInfo


class TeacherListAdapter : ListAdapter<UserInfo, TeacherListAdapter.VH>(object : DiffUtil.ItemCallback<UserInfo>() {
    override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
) {

    val itemClickRelay = PublishRelay.create<Pair<View, UserInfo>>()

    inner class VH(val binding: ItemTeacherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userInfo: UserInfo) = binding.apply {
//            this.userInfo = userInfo
//            binding.ivAvatar.bindImg(url = userInfo.avatarUrl)
//            val adapter = SubjectLabelListAdapter()
//            rvSubject.adapter = adapter
//            val subjectList = userInfo.teacherInfo!!.subjectList.map(Subject::name).toSet().toList()
//            adapter.submitData(subjectList)
//            itemView.setOnClickListener { itemClickRelay.accept(it to userInfo) }
//            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val userInfo = getItem(position)
        holder.bind(userInfo)
    }

}