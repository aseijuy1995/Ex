package edu.yujie.mvcex.model.bean

import edu.yujie.mvcex.model.ItemXXXX

data class RepositoriesBean(
    val incomplete_results: Boolean,
    val items: List<ItemXXXX>,
    val total_count: Int
):DataBean