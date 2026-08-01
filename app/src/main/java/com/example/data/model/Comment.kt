package com.example.data.model

data class Comment(
    val id: String = "",
    val videoId: String = "",
    val userId: String = "",
    val userName: String = "",
    val userAvatar: String = "",
    val text: String = "",
    val likesCount: Int = 0,
    val createdAt: String = "Just now",
    val isLiked: Boolean = false,
    val repliesCount: Int = 0
)
