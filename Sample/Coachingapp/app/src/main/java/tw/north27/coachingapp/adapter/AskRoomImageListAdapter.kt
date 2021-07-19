package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.PublishRelay
import com.yujie.core_lib.adapter.bindImg
import tw.north27.coachingapp.databinding.ItemAskRoomImageBinding
import tw.north27.coachingapp.model.AskImage

class AskRoomImageListAdapter : ListAdapter<AskImage, AskRoomImageListAdapter.VH>(
    object : DiffUtil.ItemCallback<AskImage>() {
        override fun areItemsTheSame(oldItem: AskImage, newItem: AskImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AskImage, newItem: AskImage): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {
    val itemClickRelay = PublishRelay.create<Pair<View, AskImage>>()

    inner class VH(val binding: ItemAskRoomImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(askImage: AskImage) = binding.apply {
            this.askImage = askImage
            ivImg.bindImg(url = askImage.url)
            itemView.setOnClickListener { itemClickRelay.accept(it to askImage) }
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAskRoomImageBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

}