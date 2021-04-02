package tw.north27.coachingapp.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.NotifyListAdapter
import tw.north27.coachingapp.base.BaseDataBindingFragment
import tw.north27.coachingapp.databinding.FragmentNotifyBinding
import tw.north27.coachingapp.ext.startAlphaBreatheAnim
import tw.north27.coachingapp.ext.stop
import tw.north27.coachingapp.notify.BaseLoadStateAdapter
import tw.north27.coachingapp.notify.NotifyViewModel

//https://github.com/android/architecture-components-samples/issues/281
//https://gist.github.com/guness/df12d8cc4f595af1395f4a1f5bca5f00

class NotifyFragment : BaseDataBindingFragment<FragmentNotifyBinding>(R.layout.fragment_notify) {

    private val viewModel by sharedViewModel<NotifyViewModel>()

    private lateinit var loadAdapter: BaseLoadStateAdapter

    private val adapter = NotifyListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@NotifyFragment.viewModel
        }
        loadAdapter = BaseLoadStateAdapter(viewLifecycleOwner, compositeDisposable)

        binding.rvNotify.apply {
            addItemDecoration(DividerItemDecoration(cxt, LinearLayoutManager.VERTICAL).apply {
                setDrawable(ContextCompat.getDrawable(cxt, R.drawable.shape_size_height_5_solid_gray) ?: return)
            })
            adapter = this@NotifyFragment.adapter.withLoadStateFooter(loadAdapter)
        }

        viewModel.getNotifyList().observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                if (it.refresh is LoadState.Loading) {
                    binding.itemNotifyShinner.shimmerFrameLayoutNotify.startAlphaBreatheAnim()
                    binding.itemEmpty.clEmpty.isVisible = false
                    binding.rvNotify.isVisible = false
                }
                if (it.refresh is LoadState.NotLoading) {
                    binding.smartRefreshLayoutNotify.finishRefresh()
                    binding.itemNotifyShinner.shimmerFrameLayoutNotify.stop()
                    if (adapter.itemCount > 0)
                        binding.rvNotify.isVisible = true
                    else
                        binding.itemEmpty.clEmpty.isVisible = true
                }
            }
        }

        //Refresh
        binding.smartRefreshLayoutNotify.setOnRefreshListener { adapter.refresh() }

        //Retry
        loadAdapter.retryClickRelay.subscribeWithRxLife { adapter.retry() }

        /**
         *
         * */
        viewModel.notifyDelete.observe(viewLifecycleOwner) {
            adapter.refresh()
        }

        /**
         * Default value未建立
         * */
        //Notify click
        binding.ivNotify.clicks().subscribeWithRxLife {
            showLoadingDialog()
            when (binding.ivNotify.drawable.current.constantState) {
                ContextCompat.getDrawable(cxt, R.drawable.ic_baseline_notifications_24_white)?.constantState -> viewModel.switchNotify(false)
                ContextCompat.getDrawable(cxt, R.drawable.ic_baseline_notifications_off_24_white)?.constantState -> viewModel.switchNotify(true)
            }
        }

        // Read All click
        binding.ivReadAll.clicks().subscribeWithRxLife {
            showLoadingDialog()
            viewModel.readAllNotify()
        }

        /**
         * Item click
         * 導頁功能未處理
         * */
        adapter.itemClickRelay.subscribeWithRxLife {
            Snackbar.make(binding.root, "Item Click, ${it.second.title}", Snackbar.LENGTH_SHORT).show()
        }

        /**
         * More click
         * 功能未處理
         * */
        adapter.moreClickRelay.subscribeWithRxLife {
            findNavController().navigate(NotifyFragmentDirections.actionFragmentNotifyToFragmentNotifyMoreDialog(it.second))
        }

        viewModel.toast.observe(viewLifecycleOwner, ::onToastObs)

    }

    private fun onToastObs(pair: Pair<NotifyViewModel.ToastType, String>) {
        when (pair.first) {
            NotifyViewModel.ToastType.OPEN_NOTIFY -> {
                dismissLoadingDialog()
                Snackbar.make(binding.root, pair.second, Snackbar.LENGTH_SHORT).show()
            }
            NotifyViewModel.ToastType.READ_ALL_NOTIFY -> {
                adapter.refresh()
                dismissLoadingDialog()
                Snackbar.make(binding.root, pair.second, Snackbar.LENGTH_SHORT).show()
            }
            NotifyViewModel.ToastType.DELETE_NOTIFY -> {
                Snackbar.make(binding.root, pair.second, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

}