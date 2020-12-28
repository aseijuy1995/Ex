package edu.yujie.mviex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import edu.yujie.mviex.databinding.ActivityMviBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MviActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMviBinding
    private val adapter = ItemListAdapter()
    private val viewModel by viewModel<SearchViewModel>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMviBinding>(this, R.layout.activity_mvi)
        initView()

        binding.btnSearch.clicks().subscribe {
            val text = binding.etSearch.text.toString()

            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.searchViewEvent.send(SearchViewEvent.FetchEmpty(text))
            }
        }.addTo(compositeDisposable)

        observeViewModel()
    }

    private fun initView() {
        binding.rvView.apply {
            layoutManager = LinearLayoutManager(this@MviActivity)
            adapter = this@MviActivity.adapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.searchViewStateFlow.collect {
                when (it) {
                    is SearchViewState.Idle -> {
                    }
                    is SearchViewState.Loading -> {
                        binding.rvView.isVisible = false
                        binding.progressBar.isVisible = true
                        binding.tvEmpty.isVisible = false
                    }
                    is SearchViewState.Complete -> {
                        binding.progressBar.isVisible = false
                        adapter.submitList(it.repo.items)
                        binding.rvView.isVisible = true
                    }
                    is SearchViewState.Error -> {
                        binding.progressBar.isVisible = false
                        binding.rvView.isVisible = false
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.emptyViewState.collect {
                when (it) {
                    is EmptyViewState.Idle -> {
                        binding.progressBar.isVisible = false
                    }
                    is EmptyViewState.Empty -> {
                        binding.progressBar.isVisible = false
                        binding.tvEmpty.isVisible = true
                        adapter.submitList(listOf())
                        Snackbar.make(binding.btnSearch, "Can not Empty", Snackbar.LENGTH_SHORT).show()
                    }
                    is EmptyViewState.NotEmpty -> {
                        binding.progressBar.isVisible = true
                        binding.tvEmpty.isVisible = false
                        viewModel.searchViewEvent.send(SearchViewEvent.FetchSearch(it.text!!))
                    }
                }
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}