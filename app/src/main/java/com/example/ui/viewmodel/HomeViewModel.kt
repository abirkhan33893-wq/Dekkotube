package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.model.Video
import com.example.data.repository.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val videoRepository: VideoRepository = VideoRepository()
) : ViewModel() {

    val allVideos: StateFlow<List<Video>> = videoRepository.videos
    val shortsList: StateFlow<List<Video>> = videoRepository.shorts

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    val categories = listOf("All", "Trending", "Tech", "Gaming", "Music", "Vlogs", "Education", "Comedy")

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun toggleLike(videoId: String) {
        videoRepository.toggleLikeVideo(videoId)
    }

    fun toggleSave(videoId: String) {
        videoRepository.toggleSaveVideo(videoId)
    }

    fun refreshFeed() {
        _isRefreshing.value = true
        // Simulate quick pull-to-refresh
        _isRefreshing.value = false
    }
}
