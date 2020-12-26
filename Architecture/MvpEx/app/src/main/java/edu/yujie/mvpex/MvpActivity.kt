package edu.yujie.mvpex

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import edu.yujie.mvpex.bean.RepoBean
import edu.yujie.mvpex.databinding.ActivityMvpBinding
import org.koin.android.ext.android.inject

class MvpActivity : AppCompatActivity(), IView {
    private lateinit var binding: ActivityMvpBinding
    private val adapter = ItemListAdapter()
    private val service by inject<IApiService>()
    private val searchPresenter = SearchPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMvpBinding>(this, R.layout.activity_mvp)
        initView(binding)

        binding.btnSearch.clicks().subscribe {
            binding.progressBar.visibility = View.VISIBLE
            val text = binding.etSearch.text.toString()
            if (!searchPresenter.checkEmpty(text)) {
                searchPresenter.search(text)
            }
        }

    }

    private fun initView(binding: ActivityMvpBinding) {
        binding.rvView.apply {
            layoutManager = LinearLayoutManager(this@MvpActivity)
            adapter = this@MvpActivity.adapter
        }
    }

    override fun onSearching() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvEmpty.visibility = View.GONE
    }

    override fun onSearchEmpty() {
        binding.progressBar.visibility = View.GONE
        binding.tvEmpty.visibility = View.VISIBLE
        adapter.submit(listOf())
        Snackbar.make(binding.btnSearch, "Can not Empty", Snackbar.LENGTH_SHORT).show()
    }

    override fun onSearchComplete(repoBean: RepoBean?) {
        repoBean?.let {
            adapter.submit(it.items)
        }
        binding.progressBar.visibility = View.GONE
    }
}