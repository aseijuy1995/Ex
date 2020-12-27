package edu.yujie.mvvmex.bean

import edu.yujie.mvvmex.bean.ItemBean

data class RepoBean(
    val incomplete_results: Boolean,
    val items: List<ItemBean>,
    val total_count: Int
)