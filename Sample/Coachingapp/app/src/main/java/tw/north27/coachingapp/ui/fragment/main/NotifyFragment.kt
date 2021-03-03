package tw.north27.coachingapp.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.NotifyListAdapter
import tw.north27.coachingapp.base.BaseCoachingViewBindingFragment
import tw.north27.coachingapp.databinding.FragmentNotifyBinding
import tw.north27.coachingapp.page.BaseLoadStateAdapter
import tw.north27.coachingapp.viewModel.NotifyViewModel

class NotifyFragment : BaseCoachingViewBindingFragment<FragmentNotifyBinding>(FragmentNotifyBinding::inflate) {

    private val adapter = NotifyListAdapter()

    private val viewModel by sharedViewModel<NotifyViewModel>()

    private lateinit var loadAdapter: BaseLoadStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadAdapter = BaseLoadStateAdapter(viewLifecycleOwner, compositeDisposable)
        binding.rvNotify.apply {
            addItemDecoration(DividerItemDecoration(cxt, LinearLayoutManager.VERTICAL).apply {
                setDrawable(ContextCompat.getDrawable(cxt, R.drawable.shape_size_height_5_solid_gray) ?: return)
            })
            adapter = this@NotifyFragment.adapter.withLoadStateFooter(footer = loadAdapter)
        }

        //Retry click
        loadAdapter.retryClickRelay.subscribeWithRxLife {

        }

//        binding.smartRefreshLayoutNotify.isRefreshing =

        binding.smartRefreshLayoutNotify.setOnRefreshListener {

        }

        viewModel.notifyInfoList().observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }

//        //item click
//        adapter.itemClickRelay.subscribeWithRxLife {
//            Snackbar.make(binding.root, "Notify: ${it.second.title}", Snackbar.LENGTH_SHORT).show()
//        }
//        //more click
//        adapter.moreClickRelay.subscribeWithRxLife {
//            viewModel.setNotifyInfoToMorePage(it.second)
//            findNavController().navigate(NotifyFragmentDirections.actionFragmentNotifyToFragmentNotifyMoreDialog())
//        }

    }
}