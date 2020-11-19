//package edu.yujie.pagingex.paging3
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ProgressBar
//import androidx.core.view.isVisible
//import androidx.databinding.DataBindingUtil
//import androidx.paging.LoadState
//import androidx.paging.LoadStateAdapter
//import androidx.recyclerview.widget.RecyclerView
//import edu.yujie.pagingex.R
//import edu.yujie.pagingex.databinding.ItemLoadBinding
//
//class ConcertLoadAdapter(private val adapter: Concert2DataAdapter) : LoadStateAdapter<ConcertLoadAdapter.VH>() {
//    private val TAG = javaClass.simpleName
//
////    inner class VH(private val binding: ItemLoadBinding) : RecyclerView.ViewHolder(binding.root) {
//    inner class VH(private val view: View) : RecyclerView.ViewHolder(view) {
//        fun bind(loadState: LoadState) {
////            println("$TAG:bind")
////            binding.tvErr.isVisible = loadState is LoadState.Error
////            binding.progressBar.isVisible = loadState is LoadState.Loading
////            binding.tvErr.setOnClickListener {
////                adapter.retry()
////            }
//
//            val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
//            progressBar.isVisible = loadState is LoadState.Loading
//
//        }
//    }
//
//    override fun onBindViewHolder(holder: VH, loadState: LoadState) {
//        holder.bind(loadState)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): VH {
////        val inflater = LayoutInflater.from(parent.context)
////        val binding = DataBindingUtil.inflate<ItemLoadBinding>(inflater, R.layout.item_load, parent, false)
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_load,parent,false)
////        return VH(binding)
//        return VH(view)
//    }
//}