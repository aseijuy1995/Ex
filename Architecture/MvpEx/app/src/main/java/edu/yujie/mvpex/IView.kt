package edu.yujie.mvpex

import edu.yujie.mvpex.bean.RepoBean

interface IView {

    fun onSearching()

    fun onSearchEmpty()

    fun onSearchComplete(repoBean:RepoBean?)
}