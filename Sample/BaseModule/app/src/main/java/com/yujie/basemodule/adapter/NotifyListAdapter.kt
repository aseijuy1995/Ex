package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.PublishRelay
import tw.north27.coachingapp.databinding.ItemNotifyBinding
import tw.north27.coachingapp.ext2.dataBinding
import tw.north27.coachingapp.model.result.NotifyInfo

class NotifyListAdapter : PagingDataAdapter<NotifyInfo, NotifyListAdapter.VH>(
    object : DiffUtil.ItemCallback<NotifyInfo>() {
        override fun areItemsTheSame(oldItem: NotifyInfo, newItem: NotifyInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NotifyInfo, newItem: NotifyInfo): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.imgUrl == newItem.imgUrl
                    && oldItem.title == newItem.title
                    && oldItem.desc == newItem.desc
                    && oldItem.time == newItem.time
                    && oldItem.notifyType == newItem.notifyType
        }
    }
) {

    val itemClickRelay = PublishRelay.create<Pair<View, NotifyInfo>>()

    val moreClickRelay = PublishRelay.create<Pair<View, NotifyInfo>>()

    inner class VH(val binding: ItemNotifyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notifyInfo: NotifyInfo?) = binding.apply {
            this.notifyInfo = notifyInfo
            notifyInfo?.let { notifyInfo ->
                itemView.setOnClickListener { view -> itemClickRelay.accept(view to notifyInfo) }
                ivMore.setOnClickListener { view -> moreClickRelay.accept(view to notifyInfo) }
            }
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding by dataBinding<ItemNotifyBinding, VH>(ItemNotifyBinding::inflate, LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}

