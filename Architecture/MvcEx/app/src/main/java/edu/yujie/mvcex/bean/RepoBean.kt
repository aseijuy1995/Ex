package edu.yujie.mvcex

data class RepoBean(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)