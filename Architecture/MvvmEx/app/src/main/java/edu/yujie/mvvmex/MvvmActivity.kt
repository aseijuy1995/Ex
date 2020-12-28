package edu.yujie.mvvmex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import edu.yujie.mvvmex.databinding.ActivityMvvmBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MvvmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMvvmBinding
    private val viewModel by viewModel<SearchViewModel>()
    private val adapter = ItemListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMvvmBinding>(this, R.layout.activity_mvvm).apply {
            lifecycleOwner = this@MvvmActivity
            viewModel = this@MvvmActivity.viewModel
        }
        initView(binding)

        binding.btnSearch.clicks().subscribe {
            val text = binding.etSearch.text.toString()
            binding.progressBar.isVisible = true
            binding.rvView.isVisible = false
            binding.tvEmpty.isVisible = false
            viewModel.checkEmpty(text)
        }

        viewModel.isEmptyLiveData.observe(this) {
            if (it) {
                binding.progressBar.isVisible = false
                binding.tvEmpty.isVisible = true
                adapter.submit(listOf())
                Snackbar.make(binding.btnSearch, "Can not Empty", Snackbar.LENGTH_SHORT).show()
            } else {
                val text = viewModel.searchKeyWork.value!!
                viewModel.searchRepo(text)
            }
        }

        viewModel.repoLiveData.observe(this) {
            binding.progressBar.isVisible = false
            binding.rvView.isVisible = true
            adapter.submit(it.items)
        }

    }

    private fun initView(binding: ActivityMvvmBinding) {
        binding.rvView.apply {
            layoutManager = LinearLayoutManager(this@MvvmActivity)
            adapter = this@MvvmActivity.adapter
        }
    }
}