package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.model.User
import com.example.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class AppThemeMode { SYSTEM, DARK, LIGHT }

class SettingsViewModel : ViewModel() {
    private val _themeMode = MutableStateFlow(AppThemeMode.DARK)
    val themeMode: StateFlow<AppThemeMode> = _themeMode.asStateFlow()

    private val _autoplayEnabled = MutableStateFlow(true)
    val autoplayEnabled: StateFlow<Boolean> = _autoplayEnabled.asStateFlow()

    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled.asStateFlow()

    fun setThemeMode(mode: AppThemeMode) {
        _themeMode.value = mode
    }

    fun setAutoplay(enabled: Boolean) {
        _autoplayEnabled.value = enabled
    }

    fun setNotifications(enabled: Boolean) {
        _notificationsEnabled.value = enabled
    }
}

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    val currentUser: StateFlow<User?> = authRepository.currentUser
    val isLoggedIn: StateFlow<Boolean> = authRepository.isLoggedIn

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    suspend fun login(email: String, pass: String): Boolean {
        if (email.isBlank() || pass.isBlank()) {
            _errorMessage.value = "Please enter both email and password"
            return false
        }
        _isLoading.value = true
        _errorMessage.value = null
        val result = authRepository.signIn(email, pass)
        _isLoading.value = false
        return if (result.isSuccess) {
            true
        } else {
            _errorMessage.value = result.exceptionOrNull()?.message ?: "Login failed"
            false
        }
    }

    suspend fun signup(name: String, username: String, email: String, pass: String): Boolean {
        if (name.isBlank() || username.isBlank() || email.isBlank() || pass.isBlank()) {
            _errorMessage.value = "Please fill in all mandatory fields"
            return false
        }
        _isLoading.value = true
        _errorMessage.value = null
        val result = authRepository.signUp(name, username, email, pass)
        _isLoading.value = false
        return if (result.isSuccess) {
            true
        } else {
            _errorMessage.value = result.exceptionOrNull()?.message ?: "Signup failed"
            false
        }
    }

    fun updateProfile(name: String, username: String, bio: String, avatarUrl: String, bannerUrl: String) {
        val current = currentUser.value ?: return
        val updated = current.copy(
            name = name.ifEmpty { current.name },
            username = username.ifEmpty { current.username },
            bio = bio,
            avatarUrl = avatarUrl.ifEmpty { current.avatarUrl },
            bannerUrl = bannerUrl.ifEmpty { current.bannerUrl }
        )
        authRepository.updateProfile(updated)
    }

    fun signOut() {
        authRepository.signOut()
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
