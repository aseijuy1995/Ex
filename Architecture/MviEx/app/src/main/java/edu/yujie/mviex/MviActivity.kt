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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel

class MviActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMviBinding
    private val adapter = ItemListAdapter()
    private val viewModel by viewModel<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMviBinding>(this, R.layout.activity_mvi)
        initView()

        binding.btnSearch.clicks().subscribe {
            val text = binding.etSearch.text.toString()

            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.searchIntent.send(SearchIntent.FetchEmpty(text))
            }
        }

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
            viewModel.searchStateFlow.collect {
                when (it) {
                    is SearchState.Idle -> {
                    }
                    is SearchState.Loading -> {
                        binding.rvView.isVisible = false
                        binding.progressBar.isVisible = true
                        binding.tvEmpty.isVisible = false
                    }
                    is SearchState.Empty -> {
                        binding.progressBar.isVisible = false
                        binding.tvEmpty.isVisible = true
                        adapter.submitList(listOf())
                        Snackbar.make(binding.btnSearch, "Can not Empty", Snackbar.LENGTH_SHORT).show()
                    }
                    is SearchState.NoEmpty -> {
                        binding.tvEmpty.isVisible = false
                        viewModel.searchIntent.send(SearchIntent.FetchSearch(it.text))
                    }
                    is SearchState.Complete -> {
                        binding.progressBar.isVisible = false
                        adapter.submitList(it.repo.items)
                        lifecycleScope.launch(Dispatchers.IO) {
                            delay(1000)
                            withContext(Dispatchers.Main) {
                                binding.rvView.isVisible = true
                            }
                        }
                    }
                    is SearchState.Error -> {
                        binding.progressBar.isVisible = false
                        binding.rvView.isVisible = false
                    }
                }
            }
        }
    }
}