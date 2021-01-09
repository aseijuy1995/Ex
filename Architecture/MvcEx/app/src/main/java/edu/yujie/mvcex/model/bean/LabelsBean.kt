package edu.yujie.mvcex.model.bean

import edu.yujie.mvcex.model.ItemXXX

data class LabelsBean(
    val incomplete_results: Boolean,
    val items: List<ItemXXX>,
    val total_count: Int
):DataBean