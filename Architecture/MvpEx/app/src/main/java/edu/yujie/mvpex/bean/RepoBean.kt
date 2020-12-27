package edu.yujie.mvpex.bean

data class RepoBean(
    val incomplete_results: Boolean,
    val items: List<ItemBean>,
    val total_count: Int
)