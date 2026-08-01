package com.example.data.model

data class User(
    val id: String = "",
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val avatarUrl: String = "",
    val bannerUrl: String = "",
    val bio: String = "",
    val subscribersCount: Int = 0,
    val subscribedChannelsCount: Int = 0,
    val totalViews: Long = 0,
    val isVerified: Boolean = false,
    val isSubscribed: Boolean = false,
    val joinedDate: String = "August 2026"
)
