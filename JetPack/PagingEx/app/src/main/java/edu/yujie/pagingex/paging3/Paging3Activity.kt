package edu.yujie.pagingex.paging3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import edu.yujie.pagingex.PagingViewModel
import edu.yujie.pagingex.R
import edu.yujie.pagingex.databinding.ActivityPaging3Binding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel

class Paging3Activity : AppCompatActivity() {
    private val adapter = ConcertDataAdapter()
    private val viewModel by viewModel<PagingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPaging3Binding>(this, R.layout.activity_paging3)

        binding.rvView.apply {
            layoutManager = LinearLayoutManager(this@Paging3Activity, LinearLayoutManager.VERTICAL, false)
            adapter = this@Paging3Activity.adapter.apply {
                withLoadStateFooter(ConcertsLoadStateAdapter(this))
            }
        }

        viewModel.getConcertData().observe(this) {
            lifecycleScope.launchWhenCreated {
                adapter.submitData(it)
            }
        }

        binding.swipeLayout.setOnRefreshListener {
            adapter.refresh()
        }

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            adapter.loadStateFlow.collectLatest {
                if (it.refresh !is LoadState.Loading) binding.swipeLayout.isRefreshing = false
            }
        }
    }
}