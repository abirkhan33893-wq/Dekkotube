package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.data.model.User
import com.example.data.model.Video
import com.example.ui.components.DekkhoTopBar
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.notifications.NotificationsScreen
import com.example.ui.screens.profile.ProfileScreen
import com.example.ui.screens.shorts.ShortsScreen
import com.example.ui.screens.upload.UploadScreen
import com.example.ui.theme.DekkhoRed
import com.example.ui.viewmodel.AuthViewModel
import com.example.ui.viewmodel.HomeViewModel
import com.example.ui.viewmodel.NotificationsViewModel
import com.example.ui.viewmodel.ProfileViewModel
import com.example.ui.viewmodel.ShortsViewModel
import com.example.ui.viewmodel.UploadViewModel

@Composable
fun MainContainer(
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    shortsViewModel: ShortsViewModel,
    uploadViewModel: UploadViewModel,
    notificationsViewModel: NotificationsViewModel,
    profileViewModel: ProfileViewModel,
    onNavigateToSearch: () -> Unit,
    onNavigateToChat: () -> Unit,
    onNavigateToVideoDetail: (String) -> Unit,
    onNavigateToEditProfile: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val currentUser by authViewModel.currentUser.collectAsState()

    Scaffold(
        topBar = {
            if (selectedTab != 1) { // Hide top bar on Shorts vertical full-screen
                DekkhoTopBar(
                    currentUser = currentUser,
                    onSearchClick = onNavigateToSearch,
                    onNotificationsClick = { selectedTab = 3 },
                    onChatClick = onNavigateToChat,
                    onProfileClick = { selectedTab = 4 }
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = if (selectedTab == 1) Color.Black else MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("bottom_navigation_bar")
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 0) Icons.Filled.Home else Icons.Outlined.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = { Text("Home", fontSize = 11.sp, fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DekkhoRed,
                        selectedTextColor = DekkhoRed,
                        indicatorColor = DekkhoRed.copy(alpha = 0.15f),
                        unselectedIconColor = if (selectedTab == 1) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = if (selectedTab == 1) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("bottom_tab_home")
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 1) Icons.Filled.PlayCircle else Icons.Outlined.PlayCircle,
                            contentDescription = "Shorts"
                        )
                    },
                    label = { Text("Shorts", fontSize = 11.sp, fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DekkhoRed,
                        selectedTextColor = DekkhoRed,
                        indicatorColor = DekkhoRed.copy(alpha = 0.15f),
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("bottom_tab_shorts")
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "Upload",
                            tint = DekkhoRed
                        )
                    },
                    label = { Text("Create", fontSize = 11.sp, fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DekkhoRed,
                        selectedTextColor = DekkhoRed,
                        indicatorColor = DekkhoRed.copy(alpha = 0.15f),
                        unselectedIconColor = if (selectedTab == 1) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = if (selectedTab == 1) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("bottom_tab_upload")
                )

                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 3) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                            contentDescription = "Activity"
                        )
                    },
                    label = { Text("Activity", fontSize = 11.sp, fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DekkhoRed,
                        selectedTextColor = DekkhoRed,
                        indicatorColor = DekkhoRed.copy(alpha = 0.15f),
                        unselectedIconColor = if (selectedTab == 1) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = if (selectedTab == 1) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("bottom_tab_activity")
                )

                NavigationBarItem(
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 4) Icons.Filled.Person else Icons.Outlined.Person,
                            contentDescription = "Profile"
                        )
                    },
                    label = { Text("You", fontSize = 11.sp, fontWeight = if (selectedTab == 4) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DekkhoRed,
                        selectedTextColor = DekkhoRed,
                        indicatorColor = DekkhoRed.copy(alpha = 0.15f),
                        unselectedIconColor = if (selectedTab == 1) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = if (selectedTab == 1) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag("bottom_tab_profile")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> HomeScreen(
                    homeViewModel = homeViewModel,
                    onVideoClick = { video -> onNavigateToVideoDetail(video.id) },
                    onShortClick = { shortItem -> selectedTab = 1 },
                    onChannelClick = { selectedTab = 4 }
                )
                1 -> ShortsScreen(
                    shortsViewModel = shortsViewModel,
                    currentUser = currentUser,
                    onChannelClick = { selectedTab = 4 }
                )
                2 -> UploadScreen(
                    uploadViewModel = uploadViewModel,
                    currentUser = currentUser,
                    onUploadCompleted = { selectedTab = 0 }
                )
                3 -> NotificationsScreen(
                    notificationsViewModel = notificationsViewModel,
                    onNotificationClick = { item ->
                        if (item.targetId.isNotEmpty()) {
                            onNavigateToVideoDetail(item.targetId)
                        }
                    }
                )
                4 -> ProfileScreen(
                    user = currentUser,
                    profileViewModel = profileViewModel,
                    onEditProfileClick = onNavigateToEditProfile,
                    onSettingsClick = onNavigateToSettings,
                    onVideoClick = { video -> onNavigateToVideoDetail(video.id) }
                )
            }
        }
    }
}
