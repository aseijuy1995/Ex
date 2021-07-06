package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.jakewharton.rxrelay3.PublishRelay
import com.yujie.utilmodule.adapter.bindImg
import tw.north27.coachingapp.R
import tw.north27.coachingapp.consts.dateToString
import tw.north27.coachingapp.databinding.ItemAskBinding
import tw.north27.coachingapp.model.AskRoom
import tw.north27.coachingapp.model.response.EducationLevel
import tw.north27.coachingapp.model.response.Grade
import tw.north27.coachingapp.model.response.Subject
import tw.north27.coachingapp.model.response.Units


class AskListAdapter : ListAdapter<AskRoom, AskListAdapter.VH>(object : DiffUtil.ItemCallback<AskRoom>() {

    override fun areItemsTheSame(oldItem: AskRoom, newItem: AskRoom): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AskRoom, newItem: AskRoom): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
) {

    val itemClickRelay = PublishRelay.create<Pair<View, AskRoom>>()

    val soundClickRelay = PublishRelay.create<Pair<View, AskRoom>>()

    val delClickRelay = PublishRelay.create<Pair<View, AskRoom>>()

    var educationLevelList: List<EducationLevel> = emptyList()

    var gradeList: List<Grade> = emptyList()

    var subjectList: List<Subject> = emptyList()

    var unitsList: List<Units> = emptyList()

    inner class VH(val binding: ItemAskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(askRoom: AskRoom) = binding.apply {
            this.askInfo = askRoom
            ivAvatar.bindImg(url = askRoom.senderUserInfo.avatarUrl, roundingRadius = 30)
            tvName.text = askRoom.senderUserInfo.name
            ivNotice.bindImg(resId = if (askRoom.isSound) R.drawable.ic_baseline_volume_up_24_blue else R.drawable.ic_baseline_volume_off_24_red)
            tvTime.text = dateToString(askRoom.sendTime)
            tvContent.text = askRoom.msg
            itemEducationLevel.tvLabel.text = educationLevelList?.find { it.id == askRoom.educationLevelId }?.name
            itemGrade.tvLabel.text = gradeList?.find { it.id == askRoom.gradeId }?.name
            itemSubject.tvLabel.text = subjectList?.find { it.id == askRoom.subjectId }?.name
            itemUnit.tvLabel.text = unitsList?.find { it.id == askRoom.unitId }?.name
            tvBadge.apply {
                isVisible = (askRoom.unreadNum > 0)
                text = if (askRoom.unreadNum > 100) "99+" else askRoom.unreadNum.toString()
            }
            itemAskSwipe.apply {
                ivSound.setOnClickListener { soundClickRelay.accept(it to askRoom) }
                ivDel.setOnClickListener { delClickRelay.accept(it to askRoom) }
            }
            itemView.setOnClickListener {
                if (slLayout.openStatus == SwipeLayout.Status.Open)
                    slLayout.close()
                else if (slLayout.openStatus == SwipeLayout.Status.Middle)
                    null
                else
                    itemClickRelay.accept(it to askRoom)
            }
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemAskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

}