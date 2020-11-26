package edu.yujie.pagingex.paging3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import edu.yujie.pagingex.R
import edu.yujie.pagingex.constant.PagingViewModel
import edu.yujie.pagingex.databinding.ActivityPaging3Binding
import edu.yujie.pagingex.paging2.ConcertLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class Paging3Activity : AppCompatActivity() {
    private val adapter = ConcertDataAdapter()
    private val viewModel by viewModel<PagingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPaging3Binding>(this, R.layout.activity_paging3)

        binding.rvView.apply {
            layoutManager = LinearLayoutManager(this@Paging3Activity)
            adapter = this@Paging3Activity.adapter.withLoadStateFooter(
                footer = ConcertLoadStateAdapter(this@Paging3Activity.adapter::retry)
            )
        }
        adapter.addLoadStateListener {
//            binding.swipeLayout.isRefreshing = it.refresh is LoadState.Loading
            binding.progressBar.isVisible = it.refresh is LoadState.Loading
            binding.tvErr.isVisible = it.refresh is LoadState.Error
            binding.rvView.isVisible = it.refresh is LoadState.NotLoading
        }

        lifecycleScope.launch {
            viewModel.concertListFlow.collectLatest {
                adapter.submitData(it)
            }
        }

//        binding.swipeLayout.setOnRefreshListener {
//            adapter.refresh()
//        }
    }
}