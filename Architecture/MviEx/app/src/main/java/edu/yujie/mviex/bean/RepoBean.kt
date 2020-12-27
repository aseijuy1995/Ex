package edu.yujie.mviex.bean

import edu.yujie.mviex.bean.ItemBean

data class RepoBean(
    val incomplete_results: Boolean,
    val items: List<ItemBean>,
    val total_count: Int
)