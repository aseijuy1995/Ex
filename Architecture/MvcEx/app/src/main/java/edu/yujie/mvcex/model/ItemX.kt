package edu.yujie.mvcex.model

data class ItemX(
    val author: Author,
    val comments_url: String,
    val commit: Commit,
    val committer: CommitterX,
    val html_url: String,
    val node_id: String,
    val parents: List<Parent>,
    val repository: RepositoryX,
    val score: Int,
    val sha: String,
    val url: String
)