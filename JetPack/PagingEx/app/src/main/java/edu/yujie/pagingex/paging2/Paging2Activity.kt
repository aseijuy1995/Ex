package edu.yujie.pagingex.paging2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import edu.yujie.pagingex.PagingViewModel
import edu.yujie.pagingex.R
import edu.yujie.pagingex.databinding.ActivityPaging2Binding
import org.koin.android.viewmodel.ext.android.viewModel

class Paging2Activity : AppCompatActivity() {
    private val viewModel by viewModel<PagingViewModel>()
    private val adapter = ConcertListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPaging2Binding>(this, R.layout.activity_paging2)

        binding.rvView.apply {
            layoutManager = LinearLayoutManager(this@Paging2Activity, LinearLayoutManager.VERTICAL, false)
            adapter = this@Paging2Activity.adapter
        }

        viewModel.concertList.observe(this) {
            adapter.submitList(it)
        }

//        viewModel.refreshState.observe(this) {
//            binding.swipeLayout.isRefreshing = it
//        }
//
//        viewModel.concertList.observe(this) {
//            println("concertList:observe")
//            adapter.submitList(it)
//            viewModel.refreshState.postValue(false)
////            binding.swipeLayout.isRefreshing =
//
//        }
//
//        binding.swipeLayout.setOnRefreshListener {
//            viewModel.refreshState.postValue(true)
//            viewModel.invalidate()
//        }

//        binding.swipeLayout.isRefreshing = false

    }
}