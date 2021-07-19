package tw.north27.coachingapp.adapter.ask

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
import com.yujie.core_lib.adapter.bindImg
import com.yujie.core_lib.ext.calendar
import com.yujie.core_lib.ext.isToday
import com.yujie.core_lib.pref.getId
import com.yujie.core_lib.pref.userPref
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.ItemAskBinding
import tw.north27.coachingapp.model.AskRoom
import tw.north27.coachingapp.model.AskType
import tw.north27.coachingapp.model.response.EducationLevel
import tw.north27.coachingapp.model.response.Grade
import tw.north27.coachingapp.model.response.Subject
import tw.north27.coachingapp.model.response.UnitType
import java.util.*
import kotlin.collections.ArrayList

class AskListAdapter(val cxt: Context) : ListAdapter<AskRoom, AskListAdapter.VH>(object : DiffUtil.ItemCallback<AskRoom>() {

    override fun areItemsTheSame(oldItem: AskRoom, newItem: AskRoom): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AskRoom, newItem: AskRoom): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
) {

    val clientId: String
        get() = runBlocking { cxt.userPref.getId().first() }

    val itemClickRelay = PublishRelay.create<Pair<View, AskRoom>>()

    val soundClickRelay = PublishRelay.create<Pair<View, AskRoom>>()

    val pushClickRelay = PublishRelay.create<Pair<View, AskRoom>>()

    var educationLevelList: List<EducationLevel> = emptyList()

    var gradeList: List<Grade> = emptyList()

    var subjectList: List<Subject> = emptyList()

    var unitTypeList: List<UnitType> = emptyList()

    inner class VH(val binding: ItemAskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(askRoom: AskRoom) = binding.apply {
            this.askRoom = askRoom
            val otherUser = askRoom.otherClientInfo
            ivAvatar.bindImg(url = otherUser.avatarUrl, roundingRadius = 10)
            tvName.text = otherUser.name
            ivNotice.bindImg(resId = if (askRoom.isSound) R.drawable.ic_baseline_volume_up_24_blue else R.drawable.ic_baseline_volume_off_24_red)
            itemEducationLevel.tvName.text = educationLevelList.find { it.id == askRoom.educationLevelId }?.name
            itemGrade.tvName.text = gradeList.find { it.id == askRoom.gradeId }?.name
            itemSubject.tvName.text = subjectList.find { it.id == askRoom.subjectId }?.name
            itemUnitType.tvName.text = unitTypeList.find { it.id == askRoom.unitId }?.name

            val askInfo = askRoom.askRoomInfo
            askInfo?.let {
                val calendar = calendar
                calendar.time = it.sendTime
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH) + 1
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)
                val otherDay = String.format("%d/%d/%d", year, month, day)
                val toDay = String.format("%d:%d", hour, minute)
                val format = "yyyy/MM/dd HH:mm"
                tvTime.text = if (it.sendTime.isToday(format)) toDay else otherDay
                //
                val sb = StringBuffer(if (askInfo.senderId == clientId) tvContent.context.getString(R.string.you) else otherUser.name)
                tvContent.text = when (askInfo.askType) {
                    AskType.TEXT -> askInfo.text
                    AskType.IMAGE -> sb.append(tvContent.context.getString(R.string.sent_img))
                    AskType.AUDIO -> sb.append(tvContent.context.getString(R.string.sent_audio))
                    AskType.VIDEO -> sb.append(tvContent.context.getString(R.string.sent_video))
                    else -> ""
                }
            }
            tvBadge.apply {
                isVisible = (askRoom.unreadNum > 0)
                text = if (askRoom.unreadNum > 100) "99+" else askRoom.unreadNum.toString()
            }
            itemAskSwipe.apply {
                ivPush.setOnClickListener {
                    pushClickRelay.accept(it to askRoom)
                    slLayout.close()
                }
                ivSound.setOnClickListener {
                    soundClickRelay.accept(it to askRoom)
                    slLayout.close()
                }
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

    override fun submitList(list: List<AskRoom>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    fun submitPushState(roomId: Long, state: Boolean) = submit(roomId = roomId, state = state, submitType = SubmitType.PUSH)

    fun submitSoundState(roomId: Long, state: Boolean) = submit(roomId = roomId, state = state, submitType = SubmitType.SOUND)

    /**
     * @param PUSH >> 通知
     * @param SOUND >> 聲音
     * */
    enum class SubmitType {
        PUSH,
        SOUND
    }
}

/**
 * 提交更新指定狀態
 * */
fun AskListAdapter.submit(roomId: Long, state: Boolean, submitType: AskListAdapter.SubmitType) {
    val list = currentList.map {
        when (it.id) {
            roomId -> {
                if (submitType == AskListAdapter.SubmitType.PUSH)
                    it.copy(isPush = state)
                else if (submitType == AskListAdapter.SubmitType.SOUND)
                    it.copy(isSound = state)
                else it
            }
            else -> it
        }
    }
    submitList(list)
}
