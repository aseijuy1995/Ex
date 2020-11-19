//package edu.yujie.pagingex.paging3
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.paging.PagingDataAdapter
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import edu.yujie.pagingex.Concert
//import edu.yujie.pagingex.R
//import edu.yujie.pagingex.databinding.ItemConcertBinding
//
//class Concert2DataAdapter : PagingDataAdapter<Concert, Concert2DataAdapter.VH>(sDiffCallback) {
//
//    companion object {
//        private val sDiffCallback = object : DiffUtil.ItemCallback<Concert>() {
//            override fun areItemsTheSame(oldItem: Concert, newItem: Concert): Boolean =
//                oldItem.id == newItem.id
//
//            override fun areContentsTheSame(oldItem: Concert, newItem: Concert): Boolean =
//                oldItem == newItem
//        }
//    }
//
//    inner class VH(private val binding: ItemConcertBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun binding(concert: Concert?) =
//            binding.apply {
//                this.concert = concert
//                executePendingBindings()
//            }
//    }
//
//    override fun onBindViewHolder(holder: VH, position: Int) {
//        val concert = getItem(position)
//        holder.binding(concert)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = DataBindingUtil.inflate<ItemConcertBinding>(inflater, R.layout.item_concert, parent, false)
//        return VH(binding)
//    }
//}