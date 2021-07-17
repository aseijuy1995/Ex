package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.PublishRelay
import com.yujie.core_lib.adapter.bindImg
import tw.north27.coachingapp.databinding.ItemTeacherBinding
import tw.north27.coachingapp.model.ClientInfo
import tw.north27.coachingapp.model.response.Subject
import tw.north27.coachingapp.model.response.UnitType


class TeacherListAdapter(private val act: AppCompatActivity) : ListAdapter<ClientInfo, TeacherListAdapter.VH>(object : DiffUtil.ItemCallback<ClientInfo>() {
    override fun areItemsTheSame(oldItem: ClientInfo, newItem: ClientInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ClientInfo, newItem: ClientInfo): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
) {

    val itemClickRelay = PublishRelay.create<Pair<View, ClientInfo>>()

    private var subjectList: List<Subject>? = null

    fun submitData(subjectList: List<Subject>) {
        this.subjectList = subjectList
        notifyDataSetChanged()
    }

    inner class VH(val binding: ItemTeacherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clientInfo: ClientInfo) = binding.apply {
            this.clientInfo = clientInfo
            ivAvatar.bindImg(url = clientInfo.avatarUrl)
            val subjectIdList = clientInfo.teacherInfo?.unitTypeList?.map(UnitType::subjectId)?.toSet()?.toList()
            if (subjectIdList != null && subjectIdList.isNotEmpty()) (rvSubject.adapter as SubjectLabelListAdapter).submitData(subjectIdList)
            itemView.setOnClickListener { itemClickRelay.accept(it to clientInfo) }
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