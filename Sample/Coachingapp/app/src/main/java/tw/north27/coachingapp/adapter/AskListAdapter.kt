package tw.north27.coachingapp.adapter

import android.content.Context
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
import com.yujie.utilmodule.pref.getAccount
import com.yujie.utilmodule.pref.userPref
import com.yujie.utilmodule.util.logD
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import tw.north27.coachingapp.R
import tw.north27.coachingapp.consts.dateToString
import tw.north27.coachingapp.databinding.ItemAskBinding
import tw.north27.coachingapp.model.AskRoom
import tw.north27.coachingapp.model.AskType
import tw.north27.coachingapp.model.response.EducationLevel
import tw.north27.coachingapp.model.response.Grade
import tw.north27.coachingapp.model.response.Subject
import tw.north27.coachingapp.model.response.Units


class AskListAdapter(val cxt: Context) : ListAdapter<AskRoom, AskListAdapter.VH>(object : DiffUtil.ItemCallback<AskRoom>() {

    override fun areItemsTheSame(oldItem: AskRoom, newItem: AskRoom): Boolean {
        logD("areItemsTheSame = ${oldItem.id == newItem.id}")
        logD("areItemsTheSame = oldItem.id = ${oldItem.id} /  newItem.id = ${newItem.id}")
        logD("areItemsTheSame = oldItem.text = ${oldItem.askInfo.text} /  newItem.text = ${newItem.askInfo.text}")
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AskRoom, newItem: AskRoom): Boolean {
        logD("areContentsTheSame = ${oldItem.hashCode() == newItem.hashCode()}")
        logD("areContentsTheSame = ${oldItem.askInfo.text}/${newItem.askInfo.text}")
        return oldItem.hashCode() == newItem.hashCode()
    }
}
) {

    val account: String
        get() = runBlocking { cxt.userPref.getAccount().first() }

    val itemClickRelay = PublishRelay.create<Pair<View, AskRoom>>()

    val soundClickRelay = PublishRelay.create<Pair<View, AskRoom>>()

    val delClickRelay = PublishRelay.create<Pair<View, AskRoom>>()

    var educationLevelList: List<EducationLevel> = emptyList()

    var gradeList: List<Grade> = emptyList()

    var subjectList: List<Subject> = emptyList()

    var unitsList: List<Units> = emptyList()

    inner class VH(val binding: ItemAskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(askRoom: AskRoom) = binding.apply {
            this.askRoom = askRoom
            val otherUser = askRoom.otherUserInfo
            val askInfo = askRoom.askInfo
            ivAvatar.bindImg(url = otherUser.avatarUrl, roundingRadius = 30)
            tvName.text = otherUser.name
            ivNotice.bindImg(resId = if (askRoom.isSound) R.drawable.ic_baseline_volume_up_24_blue else R.drawable.ic_baseline_volume_off_24_red)
            tvTime.text = dateToString(askInfo.sendTime)
            //
            val sb = StringBuffer(if (askInfo.senderAct == account) tvContent.context.getString(R.string.you) else otherUser.name)
            tvContent.text = when (askInfo.askType) {
                AskType.TEXT -> askInfo.text
                AskType.IMAGE -> sb.append(tvContent.context.getString(R.string.sent_img))
                AskType.AUDIO -> sb.append(tvContent.context.getString(R.string.sent_audio))
                AskType.VIDEO -> sb.append(tvContent.context.getString(R.string.sent_video))
                else -> ""
            }
            itemEducationLevel.tvName.text = educationLevelList.find { it.id == askRoom.educationLevelId }?.name
            itemGrade.tvName.text = gradeList.find { it.id == askRoom.gradeId }?.name
            itemSubject.tvName.text = subjectList.find { it.id == askRoom.subjectId }?.name
            itemUnit.tvName.text = unitsList.find { it.id == askRoom.unitId }?.name
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

//    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
//        super.onBindViewHolder(holder, position, payloads)
//        logD("onBindViewHolder:onBindViewHolder:${payloads.isEmpty()}")
//        if (payloads.isEmpty())
//            onBindViewHolder(holder, position)
//        else {
//            val askRoom = payloads[0] as AskRoom
//            logD("onBindViewHolder:askRoom = $askRoom")
//            holder.bind(askRoom)
//        }
//    }

    override fun submitList(list: List<AskRoom>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

}