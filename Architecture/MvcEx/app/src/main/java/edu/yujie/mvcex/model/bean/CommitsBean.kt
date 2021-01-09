package edu.yujie.mvcex.model.bean

import edu.yujie.mvcex.model.ItemX

data class CommitsBean(
    val incomplete_results: Boolean,
    val items: List<ItemX>,
    val total_count: Int
):DataBean