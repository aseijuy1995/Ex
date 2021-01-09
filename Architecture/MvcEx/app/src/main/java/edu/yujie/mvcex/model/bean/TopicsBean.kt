package edu.yujie.mvcex.model.bean

import edu.yujie.mvcex.model.ItemXXXXX

data class TopicsBean(
    val incomplete_results: Boolean,
    val items: List<ItemXXXXX>,
    val total_count: Int
):DataBean