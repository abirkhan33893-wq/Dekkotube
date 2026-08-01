package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.model.Comment
import com.example.data.model.User
import com.example.data.model.Video
import com.example.data.repository.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShortsViewModel(
    private val videoRepository: VideoRepository = VideoRepository()
) : ViewModel() {

    val shortsList: StateFlow<List<Video>> = videoRepository.shorts

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private val _showCommentsSheet = MutableStateFlow(false)
    val showCommentsSheet: StateFlow<Boolean> = _showCommentsSheet.asStateFlow()

    private val _activeShortComments = MutableStateFlow<List<Comment>>(emptyList())
    val activeShortComments: StateFlow<List<Comment>> = _activeShortComments.asStateFlow()

    fun onShortPageChanged(index: Int) {
        _currentIndex.value = index
    }

    fun toggleLikeShort(shortId: String) {
        videoRepository.toggleLikeVideo(shortId)
    }

    fun openComments(shortId: String) {
        _activeShortComments.value = videoRepository.getCommentsForVideo(shortId)
        _showCommentsSheet.value = true
    }

    fun closeComments() {
        _showCommentsSheet.value = false
    }

    fun addShortComment(shortId: String, text: String, user: User) {
        if (text.isBlank()) return
        videoRepository.addComment(shortId, text, user)
        _activeShortComments.value = videoRepository.getCommentsForVideo(shortId)
    }
}

class VideoDetailViewModel(
    private val videoRepository: VideoRepository = VideoRepository()
) : ViewModel() {

    private val _currentVideo = MutableStateFlow<Video?>(null)
    val currentVideo: StateFlow<Video?> = _currentVideo.asStateFlow()

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

    private val _isPlaying = MutableStateFlow(true)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _playbackSpeed = MutableStateFlow("1.0x")
    val playbackSpeed: StateFlow<String> = _playbackSpeed.asStateFlow()

    private val _videoQuality = MutableStateFlow("1080p Full HD")
    val videoQuality: StateFlow<String> = _videoQuality.asStateFlow()

    val relatedVideos: StateFlow<List<Video>> = videoRepository.videos

    fun loadVideo(videoId: String) {
        val video = videoRepository.getVideoById(videoId)
        _currentVideo.value = video
        if (video != null) {
            _comments.value = videoRepository.getCommentsForVideo(videoId)
        }
    }

    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value
    }

    fun toggleLike() {
        val id = currentVideo.value?.id ?: return
        videoRepository.toggleLikeVideo(id)
        _currentVideo.value = videoRepository.getVideoById(id)
    }

    fun toggleSave() {
        val id = currentVideo.value?.id ?: return
        videoRepository.toggleSaveVideo(id)
        _currentVideo.value = videoRepository.getVideoById(id)
    }

    fun setPlaybackSpeed(speed: String) {
        _playbackSpeed.value = speed
    }

    fun setVideoQuality(quality: String) {
        _videoQuality.value = quality
    }

    fun postComment(text: String, user: User) {
        val videoId = currentVideo.value?.id ?: return
        if (text.isBlank()) return
        videoRepository.addComment(videoId, text, user)
        _comments.value = videoRepository.getCommentsForVideo(videoId)
        _currentVideo.value = videoRepository.getVideoById(videoId)
    }
}
