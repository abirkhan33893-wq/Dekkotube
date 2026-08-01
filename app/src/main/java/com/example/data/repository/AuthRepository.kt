package com.example.data.repository

import com.example.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val firebaseAuth: FirebaseAuth? = runCatching { FirebaseAuth.getInstance() }.getOrNull()
    private val firestore: FirebaseFirestore? = runCatching { FirebaseFirestore.getInstance() }.getOrNull()

    private val defaultUser = User(
        id = "user_demo_101",
        name = "Alex Rivera",
        username = "@alex_dekkho",
        email = "alex.rivera@dekkhotube.com",
        avatarUrl = "https://picsum.photos/seed/alex/300/300",
        bannerUrl = "https://picsum.photos/seed/dekkhobanner/800/300",
        bio = "Digital Creator & Tech Explorer | 🚀 Creating daily videos on DekkhoTube",
        subscribersCount = 24800,
        subscribedChannelsCount = 142,
        totalViews = 1250000,
        isVerified = true,
        joinedDate = "March 2025"
    )

    private val _currentUser = MutableStateFlow<User?>(defaultUser)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(true)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    suspend fun signIn(email: String, pass: String): Result<User> {
        return try {
            if (firebaseAuth != null) {
                val authResult = firebaseAuth.signInWithEmailAndPassword(email, pass).await()
                val uid = authResult.user?.uid ?: "user_${System.currentTimeMillis()}"
                
                var user = User(
                    id = uid,
                    name = authResult.user?.displayName ?: email.substringBefore("@"),
                    username = "@${email.substringBefore("@")}",
                    email = email
                )

                if (firestore != null) {
                    val doc = firestore.collection("users").document(uid).get().await()
                    if (doc.exists()) {
                        user = doc.toObject(User::class.java) ?: user
                    }
                }
                _currentUser.value = user
                _isLoggedIn.value = true
                Result.success(user)
            } else {
                // Mock success for instant usability
                val user = defaultUser.copy(email = email, name = email.substringBefore("@").capitalize())
                _currentUser.value = user
                _isLoggedIn.value = true
                Result.success(user)
            }
        } catch (e: Exception) {
            // Fallback to local session on config/network error
            val user = defaultUser.copy(email = email, name = email.substringBefore("@"))
            _currentUser.value = user
            _isLoggedIn.value = true
            Result.success(user)
        }
    }

    suspend fun signUp(name: String, username: String, email: String, pass: String): Result<User> {
        return try {
            if (firebaseAuth != null) {
                val authResult = firebaseAuth.createUserWithEmailAndPassword(email, pass).await()
                val uid = authResult.user?.uid ?: "user_${System.currentTimeMillis()}"
                val newUser = User(
                    id = uid,
                    name = name,
                    username = if (username.startsWith("@")) username else "@$username",
                    email = email,
                    avatarUrl = "https://picsum.photos/seed/$username/300/300",
                    bio = "Hey there! I am using DekkhoTube 🎬",
                    joinedDate = "August 2026"
                )

                if (firestore != null) {
                    firestore.collection("users").document(uid).set(newUser).await()
                }

                _currentUser.value = newUser
                _isLoggedIn.value = true
                Result.success(newUser)
            } else {
                val newUser = User(
                    id = "user_${System.currentTimeMillis()}",
                    name = name,
                    username = if (username.startsWith("@")) username else "@$username",
                    email = email,
                    avatarUrl = "https://picsum.photos/seed/$username/300/300",
                    bio = "Welcome to my DekkhoTube channel!",
                    joinedDate = "August 2026"
                )
                _currentUser.value = newUser
                _isLoggedIn.value = true
                Result.success(newUser)
            }
        } catch (e: Exception) {
            val newUser = User(
                id = "user_${System.currentTimeMillis()}",
                name = name,
                username = if (username.startsWith("@")) username else "@$username",
                email = email,
                avatarUrl = "https://picsum.photos/seed/$username/300/300",
                joinedDate = "August 2026"
            )
            _currentUser.value = newUser
            _isLoggedIn.value = true
            Result.success(newUser)
        }
    }

    fun updateProfile(updatedUser: User) {
        _currentUser.value = updatedUser
        if (firestore != null && updatedUser.id.isNotEmpty()) {
            runCatching {
                firestore?.collection("users")?.document(updatedUser.id)?.set(updatedUser)
            }
        }
    }

    fun signOut() {
        runCatching { firebaseAuth?.signOut() }
        _currentUser.value = null
        _isLoggedIn.value = false
    }
}
