package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.model.ChatConversation
import com.example.data.model.ChatMessage
import com.example.data.model.NotificationItem
import com.example.data.model.User
import com.example.data.model.Video
import com.example.data.repository.SocialRepository
import com.example.data.repository.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UploadViewModel(
    private val videoRepository: VideoRepository = VideoRepository()
) : ViewModel() {

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading.asStateFlow()

    private val _uploadProgress = MutableStateFlow(0f)
    val uploadProgress: StateFlow<Float> = _uploadProgress.asStateFlow()

    private val _uploadSuccess = MutableStateFlow<Boolean?>(null)
    val uploadSuccess: StateFlow<Boolean?> = _uploadSuccess.asStateFlow()

    suspend fun uploadVideo(
        title: String,
        description: String,
        category: String,
        isShort: Boolean,
        currentUser: User,
        thumbnailUrl: String? = null
    ): Boolean {
        if (title.isBlank()) return false
        _isUploading.value = true
        _uploadProgress.value = 0.2f
        
        // Simulated progress steps
        _uploadProgress.value = 0.6f
        _uploadProgress.value = 0.9f
        
        videoRepository.uploadVideo(
            title = title,
            description = description,
            category = category,
            isShort = isShort,
            author = currentUser,
            customThumbnail = thumbnailUrl
        )
        
        _uploadProgress.value = 1.0f
        _isUploading.value = false
        _uploadSuccess.value = true
        return true
    }

    fun resetState() {
        _uploadSuccess.value = null
        _uploadProgress.value = 0f
        _isUploading.value = false
    }
}

class SearchViewModel(
    private val videoRepository: VideoRepository = VideoRepository()
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Video>>(emptyList())
    val searchResults: StateFlow<List<Video>> = _searchResults.asStateFlow()

    val searchHistory = listOf("Jetpack Compose Android", "Travel Vlogs 4K", "Chill Lofi Music", "Flutter vs Kotlin", "Unreal Engine 5")

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        if (newQuery.isBlank()) {
            _searchResults.value = emptyList()
        } else {
            _searchResults.value = videoRepository.searchVideos(newQuery)
        }
    }

    fun executeSearch(searchTag: String) {
        _query.value = searchTag
        _searchResults.value = videoRepository.searchVideos(searchTag)
    }

    fun clearQuery() {
        _query.value = ""
        _searchResults.value = emptyList()
    }
}

class ChatViewModel(
    private val socialRepository: SocialRepository = SocialRepository()
) : ViewModel() {

    val conversations: StateFlow<List<ChatConversation>> = socialRepository.conversations
    val currentMessages: StateFlow<List<ChatMessage>> = socialRepository.currentChatMessages

    private val _activeUser = MutableStateFlow<User?>(null)
    val activeUser: StateFlow<User?> = _activeUser.asStateFlow()

    fun selectConversation(conv: ChatConversation) {
        _activeUser.value = conv.otherUser
        socialRepository.loadMessagesForConversation(conv.id)
    }

    fun sendMessage(convId: String, text: String) {
        socialRepository.sendMessage(convId, text)
    }
}

class NotificationsViewModel(
    private val socialRepository: SocialRepository = SocialRepository()
) : ViewModel() {

    val notifications: StateFlow<List<NotificationItem>> = socialRepository.notifications

    fun markAllAsRead() {
        socialRepository.markAllNotificationsRead()
    }
}

class ProfileViewModel(
    private val videoRepository: VideoRepository = VideoRepository()
) : ViewModel() {

    val allVideos: StateFlow<List<Video>> = videoRepository.videos
    val allShorts: StateFlow<List<Video>> = videoRepository.shorts

    private val _selectedTab = MutableStateFlow(0) // 0: Videos, 1: Shorts, 2: Liked, 3: Playlists
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    fun selectTab(index: Int) {
        _selectedTab.value = index
    }
}
