package com.example.data.model

enum class NotificationType {
    LIKE, COMMENT, SUBSCRIBE, MENTION, SYSTEM, UPLOAD
}

data class NotificationItem(
    val id: String = "",
    val type: NotificationType = NotificationType.LIKE,
    val title: String = "",
    val message: String = "",
    val timestamp: String = "10m ago",
    val avatarUrl: String = "",
    val previewThumbnail: String = "",
    val targetId: String = "",
    val isRead: Boolean = false
)
