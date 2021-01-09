package edu.yujie.mvcex.model.bean

import edu.yujie.mvcex.model.ItemXX

data class IssuesBean(
    val incomplete_results: Boolean,
    val items: List<ItemXX>,
    val total_count: Int
):DataBean