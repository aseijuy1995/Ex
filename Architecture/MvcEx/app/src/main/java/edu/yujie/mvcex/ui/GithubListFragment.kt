package edu.yujie.mvcex.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.view.clicks
import edu.yujie.mvcex.R
import edu.yujie.mvcex.adapter.RepositoriesListAdapter
import edu.yujie.mvcex.databinding.FragmentGithubListBinding
import edu.yujie.mvcex.model.DataEvent
import edu.yujie.mvcex.model.SearchModel
import edu.yujie.mvcex.model.bean.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubListFragment : BaseFragment<FragmentGithubListBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_github_list

    private val searchModel = SearchModel()
    private val repositoriesListAdapter = RepositoriesListAdapter()

    private val callback = { dataBean: DataBean ->
        println("$TAG dataBean:${dataBean}")
        when (dataBean) {
            is CodeBean -> {
            }
            is CommitsBean -> {
            }
            is IssuesBean -> {
            }
            is LabelsBean -> {
            }
            is RepositoriesBean -> {
                binding.shimmerFrameLayout?.apply {
                    stopShimmer()
                    isVisible = false
                }
                binding.rvView.isVisible = true
                val itemxxxx = (dataBean as RepositoriesBean).items
                repositoriesListAdapter.submitList(itemxxxx)
            }
            is TopicsBean -> {
            }
            is UsersBean -> {
            }
            else -> {
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvView.adapter = repositoriesListAdapter
        binding.tvWelcome.clicks().subscribe {
            findNavController().navigate(R.id.fragment_github_search_dialog)
        }
        binding.floatingActionButton?.clicks()?.subscribe {
            findNavController().navigate(R.id.fragment_github_search_dialog)
        }

        RxBus.observable<DataEvent>()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.rvView.isVisible = false
                binding.shimmerFrameLayout?.apply {
                    isVisible = true
//                    val builder = Shimmer.AlphaHighlightBuilder()
//                    builder.setDuration(2500)
//                    setShimmer(builder.build())
//                    showShimmer(false)
//                    startShimmer()
                }
                if (binding.tvWelcome.isVisible) binding.tvWelcome.isVisible = false
                searchModel.search(it, callback)
            }.registerInBus(this)
    }

}