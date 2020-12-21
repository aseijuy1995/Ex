package edu.yujie.retrofitex

data class GithubBean(
    val posts: List<PostBean>,
    val comments: List<CommentBean>,
    val profile: Profile
)

data class PostBean(
    val id: Int,
    val title: String
)

data class CommentBean(
    val id: Int,
    val body: String,
    val postId: Int
)

data class Profile(
    val name: String
)