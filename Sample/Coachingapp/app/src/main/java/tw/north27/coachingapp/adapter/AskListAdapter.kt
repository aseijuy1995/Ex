package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.PublishRelay
import tw.north27.coachingapp.databinding.ItemAskBinding
import tw.north27.coachingapp.model.AskInfo


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

    val deleteClickRelay = PublishRelay.create<Pair<View, AskInfo>>()

    val itemClickRelay = PublishRelay.create<Pair<View, AskInfo>>()

    inner class VH(val binding: ItemAskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(askInfo: AskInfo) = binding.apply {
//            this.chat = askInfo
//            itemChatListSwipe.flSound.setOnClickListener { soundClickRelay.accept(it to askInfo) }
//            itemChatListSwipe.flDelete.setOnClickListener { deleteClickRelay.accept(it to askInfo) }
//            itemView.setOnClickListener {
//                if (swipeLayout.openStatus == SwipeLayout.Status.Open) swipeLayout.close()
//                else if (swipeLayout.openStatus == SwipeLayout.Status.Middle)
//                else itemClickRelay.accept(it to askInfo)
//            }
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemAskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val chat = getItem(position)
        holder.bind(chat)
    }

    override fun submitList(list: List<AskInfo>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

}