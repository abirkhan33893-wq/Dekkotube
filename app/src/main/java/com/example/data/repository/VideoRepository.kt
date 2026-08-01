package com.example.data.repository

import com.example.data.model.Comment
import com.example.data.model.Video
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VideoRepository {

    private val sampleVideos = listOf(
        Video(
            id = "vid_101",
            title = "Building Next-Gen Android Apps with Jetpack Compose & Gemini AI ✨",
            description = "Explore how modern Kotlin UI frameworks combined with generative AI models create groundbreaking Android experiences! Full step-by-step tutorial.",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            thumbnailUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=800&auto=format&fit=crop&q=80",
            duration = "14:20",
            viewsCount = 482900,
            likesCount = 38400,
            commentsCount = 1420,
            createdAt = "3 hours ago",
            channelId = "ch_tech_guru",
            channelName = "TechPulse Studio",
            channelAvatar = "https://picsum.photos/seed/techpulse/200/200",
            category = "Tech",
            isShort = false,
            tags = listOf("Android", "Kotlin", "JetpackCompose", "GeminiAI")
        ),
        Video(
            id = "vid_102",
            title = "Top 10 Epic Cinematic Travel Spots in 2026 🏔️ 4K Drone Footage",
            description = "Breathtaking views from Iceland, Swiss Alps, and Tokyo neon nights. Shot entirely in 4K high dynamic range.",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            thumbnailUrl = "https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=800&auto=format&fit=crop&q=80",
            duration = "18:45",
            viewsCount = 1250000,
            likesCount = 98200,
            commentsCount = 3890,
            createdAt = "1 day ago",
            channelId = "ch_wanderlust",
            channelName = "Wanderlust Cinema",
            channelAvatar = "https://picsum.photos/seed/wanderlust/200/200",
            category = "Vlogs",
            isShort = false,
            tags = listOf("Travel", "4K", "Drone", "Nature")
        ),
        Video(
            id = "vid_103",
            title = "Synthesizer Cyberpunk Beats to Relax / Study to 🎧 Live Stream 24/7",
            description = "Chill retro synthwave soundscapes designed for deep focus, coding, and relaxing evening vibes.",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            thumbnailUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=800&auto=format&fit=crop&q=80",
            duration = "02:45:00",
            viewsCount = 940000,
            likesCount = 75000,
            commentsCount = 2100,
            createdAt = "Streaming Live",
            channelId = "ch_lofi_lab",
            channelName = "NeoSynth Studio",
            channelAvatar = "https://picsum.photos/seed/neosynth/200/200",
            category = "Music",
            isShort = false,
            tags = listOf("LoFi", "ChillBeats", "Music", "Synthwave")
        ),
        Video(
            id = "vid_104",
            title = "Ultimate Unreal Engine 5.5 Gameplay Tech Demo - Hyper-Realistic Physics 🎮",
            description = "Deep dive preview of next-gen interactive lighting, Nanite geometry, and fluid simulation mechanics.",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
            thumbnailUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=800&auto=format&fit=crop&q=80",
            duration = "22:15",
            viewsCount = 3100000,
            likesCount = 240000,
            commentsCount = 8900,
            createdAt = "2 days ago",
            channelId = "ch_gaming_central",
            channelName = "PixelRealm Gaming",
            channelAvatar = "https://picsum.photos/seed/pixelrealm/200/200",
            category = "Gaming",
            isShort = false,
            tags = listOf("Gaming", "UE5", "Graphics", "UnrealEngine")
        ),
        Video(
            id = "vid_105",
            title = "Mastering Coffee Latte Art in 5 Minutes ☕ Barista Masterclass",
            description = "Step by step pouring techniques for tulips, rosetta, and swans at home with basic espresso equipment.",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
            thumbnailUrl = "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=800&auto=format&fit=crop&q=80",
            duration = "06:12",
            viewsCount = 620000,
            likesCount = 52000,
            commentsCount = 940,
            createdAt = "4 days ago",
            channelId = "ch_coffee_craft",
            channelName = "Coffee Culture",
            channelAvatar = "https://picsum.photos/seed/coffee/200/200",
            category = "Education",
            isShort = false,
            tags = listOf("Coffee", "LatteArt", "Tutorial", "Lifestyle")
        )
    )

    private val sampleShorts = listOf(
        Video(
            id = "short_201",
            title = "When the code compiles on the first try! 😂⚡ #developer #programmer #humor",
            description = "That rare euphoric moment every developer lives for!",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoylines.mp4",
            thumbnailUrl = "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=600&auto=format&fit=crop&q=80",
            duration = "00:25",
            viewsCount = 890000,
            likesCount = 142000,
            commentsCount = 3200,
            createdAt = "1 hour ago",
            channelId = "user_demo_101",
            channelName = "Alex Rivera",
            channelAvatar = "https://picsum.photos/seed/alex/200/200",
            category = "Comedy",
            isShort = true,
            audioTrackTitle = "Dev Vibe Sound - Alex Rivera Original"
        ),
        Video(
            id = "short_202",
            title = "Insane FPV Drone flip through a waterfall! 🌊🚁 #drone #fpv #extreme",
            description = "Pure adrenaline pilot view flying right into the mist.",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
            thumbnailUrl = "https://images.unsplash.com/photo-1527977966376-1c8408f9f108?w=600&auto=format&fit=crop&q=80",
            duration = "00:18",
            viewsCount = 2400000,
            likesCount = 380000,
            commentsCount = 7400,
            createdAt = "5 hours ago",
            channelId = "ch_sky_pilot",
            channelName = "SkyNinja FPV",
            channelAvatar = "https://picsum.photos/seed/skyninja/200/200",
            category = "Shorts",
            isShort = true,
            audioTrackTitle = "Rush Hour Electro Remix"
        ),
        Video(
            id = "short_203",
            title = "Secret iPhone & Android productivity hack you need to enable TODAY 📱💡",
            description = "Save 2 hours every single day with this quick system setting shortcut.",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
            thumbnailUrl = "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=600&auto=format&fit=crop&q=80",
            duration = "00:45",
            viewsCount = 1750000,
            likesCount = 210000,
            commentsCount = 4900,
            createdAt = "12 hours ago",
            channelId = "ch_tech_tips",
            channelName = "ByteSize Tech",
            channelAvatar = "https://picsum.photos/seed/bytesize/200/200",
            category = "Tech",
            isShort = true,
            audioTrackTitle = "Tech Beats 2026"
        ),
        Video(
            id = "short_204",
            title = "30-Second Crispy Garlic Butter Smash Burger Recipe 🍔🔥",
            description = "Sizzling caramelized crust smash burger tutorial.",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackSeeTheWorld.mp4",
            thumbnailUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=600&auto=format&fit=crop&q=80",
            duration = "00:30",
            viewsCount = 3200000,
            likesCount = 490000,
            commentsCount = 8200,
            createdAt = "1 day ago",
            channelId = "ch_chef_sam",
            channelName = "Chef Sam's Kitchen",
            channelAvatar = "https://picsum.photos/seed/chefsam/200/200",
            category = "Education",
            isShort = true,
            audioTrackTitle = "Sizzle & Chill Lounge"
        )
    )

    private val _videos = MutableStateFlow<List<Video>>(sampleVideos)
    val videos: StateFlow<List<Video>> = _videos.asStateFlow()

    private val _shorts = MutableStateFlow<List<Video>>(sampleShorts)
    val shorts: StateFlow<List<Video>> = _shorts.asStateFlow()

    private val _commentsMap = MutableStateFlow<Map<String, List<Comment>>>(
        mapOf(
            "vid_101" to listOf(
                Comment(id = "c1", videoId = "vid_101", userId = "u1", userName = "Sarah Jenkins", userAvatar = "https://picsum.photos/seed/sarah/100/100", text = "This Jetpack Compose breakdown is phenomenal! Loved the Gemini integration section.", likesCount = 142, createdAt = "2h ago"),
                Comment(id = "c2", videoId = "vid_101", userId = "u2", userName = "David Miller", userAvatar = "https://picsum.photos/seed/david/100/100", text = "The clean UI design in DekkhoTube is top tier 🔥 keep posting!", likesCount = 89, createdAt = "1h ago"),
                Comment(id = "c3", videoId = "vid_101", userId = "u3", userName = "Priya Sharma", userAvatar = "https://picsum.photos/seed/priya/100/100", text = "Could you make a video on Room offline database caching next?", likesCount = 45, createdAt = "30m ago")
            ),
            "short_201" to listOf(
                Comment(id = "sc1", videoId = "short_201", userId = "u4", userName = "Mark Vance", userAvatar = "https://picsum.photos/seed/mark/100/100", text = "Relatable 1000%! That first clean build feeling is priceless 🚀", likesCount = 530, createdAt = "45m ago")
            )
        )
    )

    fun getVideoById(id: String): Video? {
        return _videos.value.find { it.id == id } ?: _shorts.value.find { it.id == id }
    }

    fun toggleLikeVideo(id: String) {
        _videos.value = _videos.value.map { v ->
            if (v.id == id) {
                val newIsLiked = !v.isLiked
                val newLikesCount = if (newIsLiked) v.likesCount + 1 else (v.likesCount - 1).coerceAtLeast(0)
                v.copy(isLiked = newIsLiked, likesCount = newLikesCount, isDisliked = false)
            } else v
        }
        _shorts.value = _shorts.value.map { s ->
            if (s.id == id) {
                val newIsLiked = !s.isLiked
                val newLikesCount = if (newIsLiked) s.likesCount + 1 else (s.likesCount - 1).coerceAtLeast(0)
                s.copy(isLiked = newIsLiked, likesCount = newLikesCount, isDisliked = false)
            } else s
        }
    }

    fun toggleSaveVideo(id: String) {
        _videos.value = _videos.value.map { v ->
            if (v.id == id) v.copy(isSaved = !v.isSaved) else v
        }
    }

    fun getCommentsForVideo(videoId: String): List<Comment> {
        return _commentsMap.value[videoId] ?: listOf(
            Comment(
                id = "c_def_${System.currentTimeMillis()}",
                videoId = videoId,
                userId = "u_fan",
                userName = "Dekkho Fan",
                userAvatar = "https://picsum.photos/seed/dekkho/100/100",
                text = "Awesome content! Excited to see more uploads on DekkhoTube ✨",
                likesCount = 12,
                createdAt = "Just now"
            )
        )
    }

    fun addComment(videoId: String, text: String, user: com.example.data.model.User) {
        val newComment = Comment(
            id = "comment_${System.currentTimeMillis()}",
            videoId = videoId,
            userId = user.id,
            userName = user.name,
            userAvatar = user.avatarUrl.ifEmpty { "https://picsum.photos/seed/${user.name}/100/100" },
            text = text,
            likesCount = 0,
            createdAt = "Just now"
        )
        val current = _commentsMap.value[videoId] ?: emptyList()
        _commentsMap.value = _commentsMap.value + (videoId to (listOf(newComment) + current))
        
        // Update comments count
        _videos.value = _videos.value.map { v ->
            if (v.id == videoId) v.copy(commentsCount = v.commentsCount + 1) else v
        }
        _shorts.value = _shorts.value.map { s ->
            if (s.id == videoId) s.copy(commentsCount = s.commentsCount + 1) else s
        }
    }

    fun uploadVideo(
        title: String,
        description: String,
        category: String,
        isShort: Boolean,
        author: com.example.data.model.User,
        customThumbnail: String? = null
    ): Video {
        val newVideo = Video(
            id = if (isShort) "short_${System.currentTimeMillis()}" else "vid_${System.currentTimeMillis()}",
            title = title,
            description = description,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
            thumbnailUrl = customThumbnail ?: "https://images.unsplash.com/photo-1579546929518-9e396f3cc809?w=800&auto=format&fit=crop&q=80",
            duration = if (isShort) "00:45" else "08:30",
            viewsCount = 1,
            likesCount = 0,
            commentsCount = 0,
            createdAt = "Just now",
            channelId = author.id,
            channelName = author.name,
            channelAvatar = author.avatarUrl.ifEmpty { "https://picsum.photos/seed/${author.username}/200/200" },
            category = category,
            isShort = isShort,
            tags = listOf("DekkhoTube", category, "NewUpload")
        )

        if (isShort) {
            _shorts.value = listOf(newVideo) + _shorts.value
        } else {
            _videos.value = listOf(newVideo) + _videos.value
        }
        return newVideo
    }

    fun searchVideos(query: String): List<Video> {
        if (query.isBlank()) return _videos.value
        return _videos.value.filter {
            it.title.contains(query, ignoreCase = true) ||
            it.description.contains(query, ignoreCase = true) ||
            it.channelName.contains(query, ignoreCase = true) ||
            it.category.contains(query, ignoreCase = true) ||
            it.tags.any { tag -> tag.contains(query, ignoreCase = true) }
        }
    }
}
