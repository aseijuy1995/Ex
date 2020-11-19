//package edu.yujie.pagingex.paging3
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.paging.LoadState
//import androidx.paging.LoadStateAdapter
//import androidx.recyclerview.widget.RecyclerView
//import edu.yujie.pagingex.R
//import edu.yujie.pagingex.databinding.ItemHeaderBinding
//
//class ConcertsLoadStateAdapter(private val adapter: ConcertDataAdapter) : LoadStateAdapter<ConcertsLoadStateAdapter.NetworkStateItemViewHolder>() {
//
//    inner class NetworkStateItemViewHolder(private val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(loadState: LoadState) {
//            when (loadState) {
//                is LoadState.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE
//                    binding.tvError.visibility = View.GONE
//                }
//                is LoadState.Error -> {
//                    binding.progressBar.visibility = View.GONE
//                    binding.tvError.visibility = View.VISIBLE
//                }
//                else -> {
//                }
//            }
//            binding.executePendingBindings()
//            println("loadState")
//        }
//    }
//
//    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
//        holder.bind(loadState)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): NetworkStateItemViewHolder {
//        val binding = DataBindingUtil.inflate<ItemHeaderBinding>(LayoutInflater.from(parent.context), R.layout.item_header, parent, false)
//        return NetworkStateItemViewHolder(binding).apply {
//            adapter.retry()
//        }
//    }
//}