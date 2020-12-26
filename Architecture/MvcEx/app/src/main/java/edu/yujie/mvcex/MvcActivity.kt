package edu.yujie.mvcex

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import edu.yujie.mvcex.bean.RepoBean
import edu.yujie.mvcex.databinding.ActivityMvcBinding
import org.koin.android.ext.android.inject

class MvcActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMvcBinding
    private val searchModel by inject<SearchModel>()
    private val adapter = ItemListAdapter()
    private val callback = { repo: RepoBean ->
        binding.progressBar.visibility = View.GONE
        adapter.submit(repo.items)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMvcBinding>(this, R.layout.activity_mvc)
        initView(binding)

        binding.btnSearch.clicks().subscribe {
            binding.progressBar.visibility = View.VISIBLE
            val text = binding.etSearch.text.toString()
            if (searchModel.check(text)) {
                binding.progressBar.visibility = View.GONE
                adapter.submit(listOf())
                Snackbar.make(binding.btnSearch, "Can not Empty", Snackbar.LENGTH_SHORT).show()
            } else {
                searchModel.search(text, callback)
            }
        }
    }

    private fun initView(binding: ActivityMvcBinding) {
        binding.rvView.apply {
            layoutManager = LinearLayoutManager(this@MvcActivity)
            adapter = this@MvcActivity.adapter
        }
    }
}