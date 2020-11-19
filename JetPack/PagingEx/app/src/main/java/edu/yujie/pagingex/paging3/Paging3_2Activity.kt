package edu.yujie.pagingex.paging3

//import androidx.paging.LoadState
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import edu.yujie.pagingex.R
import edu.yujie.pagingex.databinding.ActivityPaging32Binding

class Paging3_2Activity : AppCompatActivity() {
//    private val adapter = Concert2DataAdapter()
//    private val loadAdapter = ConcertLoadAdapter(adapter)
//    private val viewModel by viewModel<PagingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPaging32Binding>(this, R.layout.activity_paging3_2)

//        binding.rvView.apply {
//            layoutManager = LinearLayoutManager(this@Paging3_2Activity, LinearLayoutManager.VERTICAL, false)
//            adapter = this@Paging3_2Activity.adapter.apply {
//                addLoadStateListener {
//                    //
//                    loadAdapter.loadState = it.append
//                }
//                withLoadStateFooter(loadAdapter)
//            }
//        }
//
//
//        lifecycleScope.launch(Dispatchers.IO) {
//            viewModel.getConcertFlow().collectLatest {
//                withContext(Dispatchers.Main) {
//                    adapter.submitData(it)
//                }
//            }
//        }
//
//        //refresh
//        binding.swipeLayout.setOnRefreshListener {
//            adapter.refresh()
//        }
//        adapter.retry()
//
//        lifecycleScope.launch(Dispatchers.IO) {
//            @OptIn(ExperimentalCoroutinesApi::class)
//            adapter.loadStateFlow.collectLatest {
//                withContext(Dispatchers.Main) {
//                    binding.swipeLayout.isRefreshing = it.refresh is LoadState.Loading
//                }
//            }
//        }
//
//        //load from activity ui
////        lifecycleScope.launch(Dispatchers.IO) {
////            adapter.loadStateFlow.collectLatest {
////                withContext(Dispatchers.Main) {
////                    binding.rvView.isVisible = it.refresh is LoadState.NotLoading
////                    binding.tvErr.isVisible = it.refresh is LoadState.Error
////                    binding.progressBar.isVisible = it.refresh is LoadState.Loading
////                }
////            }
////        }
//
    }
}