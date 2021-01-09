package edu.yujie.mvcex.model.bean

import edu.yujie.mvcex.model.ItemXXXXXX

data class UsersBean(
    val incomplete_results: Boolean,
    val items: List<ItemXXXXXX>,
    val total_count: Int
):DataBean