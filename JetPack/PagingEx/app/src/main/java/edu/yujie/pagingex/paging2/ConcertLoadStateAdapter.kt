package edu.yujie.pagingex.paging2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.yujie.pagingex.R
import edu.yujie.pagingex.databinding.ItemLoadBinding
import edu.yujie.pagingex.paging3.ConcertDataAdapter

class ConcertLoadStateAdapter(private val adapter: ConcertDataAdapter) : LoadStateAdapter<ConcertLoadStateAdapter.VH>() {

    inner class VH(private val binding: ItemLoadBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) =
            binding.apply {
                tvErr.apply {
                    isVisible = loadState is LoadState.Error
                    setOnClickListener {
                        adapter.retry()
                    }
                }
                progressBar.isVisible = loadState is LoadState.Loading
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemLoadBinding>(inflater, R.layout.item_load, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, loadState: LoadState) {
        holder.bind(loadState)
    }
}