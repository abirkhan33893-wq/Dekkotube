package com.example.data.repository

import com.example.data.model.ChatConversation
import com.example.data.model.ChatMessage
import com.example.data.model.NotificationItem
import com.example.data.model.NotificationType
import com.example.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SocialRepository {

    private val sampleNotifications = listOf(
        NotificationItem(
            id = "n1",
            type = NotificationType.SUBSCRIBE,
            title = "New Subscriber!",
            message = "Sarah Jenkins subscribed to your channel TechPulse Studio.",
            timestamp = "10m ago",
            avatarUrl = "https://picsum.photos/seed/sarah/150/150",
            isRead = false
        ),
        NotificationItem(
            id = "n2",
            type = NotificationType.LIKE,
            title = "Video Liked",
            message = "David Miller liked your Short: 'When the code compiles on first try! 😂'",
            timestamp = "45m ago",
            avatarUrl = "https://picsum.photos/seed/david/150/150",
            previewThumbnail = "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=300",
            isRead = false
        ),
        NotificationItem(
            id = "n3",
            type = NotificationType.COMMENT,
            title = "New Comment",
            message = "Priya Sharma commented: 'Great tutorial, looking forward to part 2!'",
            timestamp = "2h ago",
            avatarUrl = "https://picsum.photos/seed/priya/150/150",
            isRead = true
        ),
        NotificationItem(
            id = "n4",
            type = NotificationType.UPLOAD,
            title = "Wanderlust Cinema uploaded",
            message = "Watch 'Top 10 Epic Cinematic Travel Spots in 2026 🏔️ 4K Drone Footage'",
            timestamp = "1d ago",
            avatarUrl = "https://picsum.photos/seed/wanderlust/150/150",
            previewThumbnail = "https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=300",
            isRead = true
        )
    )

    private val sampleConversations = listOf(
        ChatConversation(
            id = "conv_1",
            otherUser = User(
                id = "u_sarah",
                name = "Sarah Jenkins",
                username = "@sarah_j",
                avatarUrl = "https://picsum.photos/seed/sarah/200/200",
                bio = "Tech reviewer & Mobile Developer"
            ),
            lastMessage = "Hey Alex! Loved your Jetpack Compose tutorial 🔥",
            lastMessageTime = "12:45 PM",
            unreadCount = 2,
            isOnline = true
        ),
        ChatConversation(
            id = "conv_2",
            otherUser = User(
                id = "u_david",
                name = "David Miller",
                username = "@david_m",
                avatarUrl = "https://picsum.photos/seed/david/200/200",
                bio = "Video Creator & FPV Pilot"
            ),
            lastMessage = "Let's collaborate on the next DekkhoTube Short!",
            lastMessageTime = "Yesterday",
            unreadCount = 0,
            isOnline = false
        ),
        ChatConversation(
            id = "conv_3",
            otherUser = User(
                id = "u_priya",
                name = "Priya Sharma",
                username = "@priya_design",
                avatarUrl = "https://picsum.photos/seed/priya/200/200",
                bio = "UI/UX Designer & Motion Artist"
            ),
            lastMessage = "Sent you the custom thumbnail graphics.",
            lastMessageTime = "Aug 1",
            unreadCount = 0,
            isOnline = true
        )
    )

    private val chatMessagesMap = mutableMapOf(
        "conv_1" to mutableListOf(
            ChatMessage(id = "m1", senderId = "u_sarah", text = "Hi Alex! Are you planning a part 2 video?", timestamp = "12:40 PM", isFromMe = false),
            ChatMessage(id = "m2", senderId = "user_demo_101", text = "Yes! Working on the Firebase + Firestore integration step now.", timestamp = "12:42 PM", isFromMe = true),
            ChatMessage(id = "m3", senderId = "u_sarah", text = "Awesome! Loved your Jetpack Compose tutorial 🔥", timestamp = "12:45 PM", isFromMe = false)
        )
    )

    private val _notifications = MutableStateFlow<List<NotificationItem>>(sampleNotifications)
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _conversations = MutableStateFlow<List<ChatConversation>>(sampleConversations)
    val conversations: StateFlow<List<ChatConversation>> = _conversations.asStateFlow()

    private val _currentChatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val currentChatMessages: StateFlow<List<ChatMessage>> = _currentChatMessages.asStateFlow()

    fun markAllNotificationsRead() {
        _notifications.value = _notifications.value.map { it.copy(isRead = true) }
    }

    fun loadMessagesForConversation(convId: String) {
        val msgs = chatMessagesMap[convId] ?: mutableListOf()
        _currentChatMessages.value = msgs.toList()
    }

    fun sendMessage(convId: String, text: String) {
        if (text.isBlank()) return
        val newMsg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            senderId = "user_demo_101",
            text = text,
            timestamp = "Just now",
            isFromMe = true,
            isRead = true
        )
        val list = chatMessagesMap.getOrPut(convId) { mutableListOf() }
        list.add(newMsg)
        _currentChatMessages.value = list.toList()

        // Update conversation last message
        _conversations.value = _conversations.value.map { c ->
            if (c.id == convId) c.copy(lastMessage = text, lastMessageTime = "Just now") else c
        }
    }
}
