package edu.yujie.mvcex.model.bean

import edu.yujie.mvcex.model.Item

data class CodeBean(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
):DataBean