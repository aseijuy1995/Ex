package tw.north27.coachingapp.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.NotifyListAdapter
import tw.north27.coachingapp.databinding.FragmentNotifyBinding
import tw.north27.coachingapp.module.base.fragment.BaseViewBindingFragment
import tw.north27.coachingapp.viewModel.NotifyViewModel

class NotifyFragment : BaseViewBindingFragment<FragmentNotifyBinding>(FragmentNotifyBinding::inflate) {

    private val adapter = NotifyListAdapter()

    private val viewModel by sharedViewModel<NotifyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvNotify.apply {
            addItemDecoration(DividerItemDecoration(cxt, LinearLayoutManager.VERTICAL).apply {
                setDrawable(ContextCompat.getDrawable(cxt, R.drawable.layer_shape_size_height_1_solid_gray_lt_rt_20) ?: return)
            })
            adapter = this@NotifyFragment.adapter
        }

        viewModel.notifyList().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        //item click
        adapter.itemClickRelay.subscribeWithRxLife {
            Snackbar.make(binding.root, "Notify: ${it.second.title}", Snackbar.LENGTH_SHORT).show()
        }
        //more click
        adapter.moreClickRelay.subscribeWithRxLife {
            viewModel.setNotifyInfoToMorePage(it.second)
            findNavController().navigate(NotifyFragmentDirections.actionFragmentNotifyToFragmentNotifyMoreDialog())
        }

    }
}