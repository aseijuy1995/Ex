package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.PublishRelay
import com.yujie.utilmodule.adapter.bindImg
import tw.north27.coachingapp.databinding.ItemTeacherBinding
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.model.response.Subject
import tw.north27.coachingapp.model.response.Units


class TeacherListAdapter(private val act: AppCompatActivity) : ListAdapter<UserInfo, TeacherListAdapter.VH>(object : DiffUtil.ItemCallback<UserInfo>() {
    override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
        return oldItem.account == newItem.account
    }

    override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
) {

    val itemClickRelay = PublishRelay.create<Pair<View, UserInfo>>()

    private var subjectList: List<Subject>? = null

    fun submitData(subjectList: List<Subject>) {
        this.subjectList = subjectList
        notifyDataSetChanged()
    }

    inner class VH(val binding: ItemTeacherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userInfo: UserInfo) = binding.apply {
            this.userInfo = userInfo
            ivAvatar.bindImg(url = userInfo.avatarUrl)
            val subjectIdList = userInfo.teacherInfo?.unitsList?.map(Units::subjectId)?.toSet()?.toList()
            if (subjectIdList != null && subjectIdList.isNotEmpty()) (rvSubject.adapter as SubjectLabelListAdapter).submitData(subjectIdList)
            itemView.setOnClickListener { itemClickRelay.accept(it to userInfo) }
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.rvSubject.adapter = SubjectLabelListAdapter(act)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val userInfo = getItem(position)
        holder.bind(userInfo)
    }

}