package edu.yujie.mvcex.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.view.clicks
import edu.yujie.mvcex.R
import edu.yujie.mvcex.databinding.FragmentGithubSearchDialogBinding
import edu.yujie.mvcex.model.SearchModel

class GithubSearchDialogFragment : BaseDialogFragment<FragmentGithubSearchDialogBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_github_search_dialog

    private val searchModel = SearchModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearch.clicks().subscribe {
            searchModel.getType(binding.seekBar.progress)
            searchModel.getKeyWord(binding.etKetword.text.toString())
            searchModel.sendData()
            findNavController().navigateUp()
        }
    }


}