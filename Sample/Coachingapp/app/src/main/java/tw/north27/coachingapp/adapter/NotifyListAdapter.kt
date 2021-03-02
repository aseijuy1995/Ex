package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.PublishRelay
import tw.north27.coachingapp.databinding.ItemNotifyBinding
import tw.north27.coachingapp.model.result.NotifyInfo
import tw.north27.coachingapp.module.ext.dataBinding

//class NotifyListAdapter : PagingDataAdapter<Notify, NotifyListAdapter.VH>(
class NotifyListAdapter : ListAdapter<NotifyInfo, NotifyListAdapter.VH>(
    object : DiffUtil.ItemCallback<NotifyInfo>() {
        override fun areItemsTheSame(oldItem: NotifyInfo, newItem: NotifyInfo): Boolean {
            return oldItem.id == newItem.id
//            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: NotifyInfo, newItem: NotifyInfo): Boolean {
            return oldItem.id == newItem.id
//            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {

    val itemClickRelay = PublishRelay.create<Pair<View, NotifyInfo>>()

    val moreClickRelay = PublishRelay.create<Pair<View, NotifyInfo>>()

    inner class VH(val binding: ItemNotifyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notifyInfo: NotifyInfo) = binding.apply {
            this.notifyInfo = notifyInfo
            itemView.setOnClickListener { itemClickRelay.accept(it to notifyInfo) }
            ivMore.setOnClickListener { moreClickRelay.accept(it to notifyInfo) }
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

