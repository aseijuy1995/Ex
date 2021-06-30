package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.jakewharton.rxrelay3.PublishRelay
import com.yujie.utilmodule.adapter.bindImg
import tw.north27.coachingapp.R
import tw.north27.coachingapp.consts.dateToString
import tw.north27.coachingapp.databinding.ItemAskBinding
import tw.north27.coachingapp.model.AskInfo
import tw.north27.coachingapp.model.response.Education
import tw.north27.coachingapp.model.response.Grade
import tw.north27.coachingapp.model.response.Subject
import tw.north27.coachingapp.model.response.Units


class AskListAdapter : ListAdapter<AskInfo, AskListAdapter.VH>(object : DiffUtil.ItemCallback<AskInfo>() {

    override fun areItemsTheSame(oldItem: AskInfo, newItem: AskInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AskInfo, newItem: AskInfo): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
) {

    val soundClickRelay = PublishRelay.create<Pair<View, AskInfo>>()

    val delClickRelay = PublishRelay.create<Pair<View, AskInfo>>()

    val itemClickRelay = PublishRelay.create<Pair<View, AskInfo>>()

    var educationList: List<Education>? = null

    var gradeList: List<Grade>? = null

    var subjectList: List<Subject>? = null

    var unitsList: List<Units>? = null

    inner class VH(val binding: ItemAskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(askInfo: AskInfo) = binding.apply {
            this.askInfo = askInfo
            ivAvatar.bindImg(url = askInfo.userInfo.avatarUrl, roundingRadius = 30)
            tvName.text = askInfo.userInfo.name
            ivNotice.bindImg(resId = if (askInfo.isSound) R.drawable.ic_baseline_volume_up_24_blue else R.drawable.ic_baseline_volume_off_24_red)
            tvTime.text = dateToString(askInfo.sendTime)
            tvMsg.text = askInfo.msg
            itemLabelEducation.tvLabel.text = educationList?.find { it.id == askInfo.educationId }?.name
            itemLabelGrade.tvLabel.text = gradeList?.find { it.id == askInfo.gradeId }?.name
            itemLabelSubject.tvLabel.text = subjectList?.find { it.id == askInfo.subjectId }?.name
            itemLabelUnit.tvLabel.text = unitsList?.find { it.id == askInfo.unitId }?.name
            tvBadge.apply {
                isVisible = (askInfo.unReadNum > 0)
                text = if (askInfo.unReadNum > 100) "99+" else askInfo.unReadNum.toString()
            }
            itemAskSwipe.apply {
                ivSound.setOnClickListener { soundClickRelay.accept(it to askInfo) }
                ivDel.setOnClickListener { delClickRelay.accept(it to askInfo) }
            }
            itemView.setOnClickListener {
                if (slLayout.openStatus == SwipeLayout.Status.Open)
                    slLayout.close()
                else if (slLayout.openStatus == SwipeLayout.Status.Middle)
                    null
                else
                    itemClickRelay.accept(it to askInfo)
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