package com.example.data.model

data class ChatMessage(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val mediaUrl: String? = null,
    val timestamp: String = "12:30 PM",
    val isFromMe: Boolean = false,
    val isRead: Boolean = true
)

data class ChatConversation(
    val id: String = "",
    val otherUser: User = User(),
    val lastMessage: String = "",
    val lastMessageTime: String = "",
    val unreadCount: Int = 0,
    val isOnline: Boolean = true
)
