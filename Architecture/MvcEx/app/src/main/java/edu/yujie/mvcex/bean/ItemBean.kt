package edu.yujie.mvcex.bean

data class ItemBean(
    val name: String,
    val full_name: String,
    val owner: OwnerBean,
    val html_url: String,
    val description: String
)