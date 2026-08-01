package com.example.data.model

data class Video(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val videoUrl: String = "",
    val thumbnailUrl: String = "",
    val duration: String = "03:45",
    val viewsCount: Long = 0,
    val likesCount: Long = 0,
    val commentsCount: Long = 0,
    val sharesCount: Long = 0,
    val createdAt: String = "2 hours ago",
    val timestamp: Long = System.currentTimeMillis(),
    val channelId: String = "",
    val channelName: String = "",
    val channelAvatar: String = "",
    val category: String = "General",
    val isShort: Boolean = false,
    val isLiked: Boolean = false,
    val isDisliked: Boolean = false,
    val isSaved: Boolean = false,
    val tags: List<String> = emptyList(),
    val audioTrackTitle: String = "Original Sound - DekkhoTube Creator"
)
