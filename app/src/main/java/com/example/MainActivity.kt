package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.MainContainer
import com.example.ui.screens.auth.LoginScreen
import com.example.ui.screens.auth.SignupScreen
import com.example.ui.screens.chat.ChatDetailScreen
import com.example.ui.screens.chat.ChatListScreen
import com.example.ui.screens.profile.EditProfileScreen
import com.example.ui.screens.search.SearchScreen
import com.example.ui.screens.settings.SettingsScreen
import com.example.ui.screens.splash.SplashScreen
import com.example.ui.screens.video.VideoDetailScreen
import com.example.ui.theme.DekkhoTubeTheme
import com.example.ui.viewmodel.AppThemeMode
import com.example.ui.viewmodel.AuthViewModel
import com.example.ui.viewmodel.ChatViewModel
import com.example.ui.viewmodel.HomeViewModel
import com.example.ui.viewmodel.NotificationsViewModel
import com.example.ui.viewmodel.ProfileViewModel
import com.example.ui.viewmodel.SearchViewModel
import com.example.ui.viewmodel.SettingsViewModel
import com.example.ui.viewmodel.ShortsViewModel
import com.example.ui.viewmodel.UploadViewModel
import com.example.ui.viewmodel.VideoDetailViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val themeMode by settingsViewModel.themeMode.collectAsState()

            val darkTheme = when (themeMode) {
                AppThemeMode.DARK -> true
                AppThemeMode.LIGHT -> false
                AppThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            DekkhoTubeTheme(darkTheme = darkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DekkhoTubeApp(settingsViewModel = settingsViewModel)
                }
            }
        }
    }
}

@Composable
fun DekkhoTubeApp(
    settingsViewModel: SettingsViewModel
) {
    val navController = rememberNavController()

    val authViewModel: AuthViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()
    val shortsViewModel: ShortsViewModel = viewModel()
    val uploadViewModel: UploadViewModel = viewModel()
    val notificationsViewModel: NotificationsViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    val videoDetailViewModel: VideoDetailViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel()
    val chatViewModel: ChatViewModel = viewModel()

    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate("main") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("login") {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate("signup")
                }
            )
        }

        composable("signup") {
            SignupScreen(
                authViewModel = authViewModel,
                onSignupSuccess = {
                    navController.navigate("main") {
                        popUpTo("signup") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate("login")
                }
            )
        }

        composable("main") {
            MainContainer(
                authViewModel = authViewModel,
                homeViewModel = homeViewModel,
                shortsViewModel = shortsViewModel,
                uploadViewModel = uploadViewModel,
                notificationsViewModel = notificationsViewModel,
                profileViewModel = profileViewModel,
                onNavigateToSearch = { navController.navigate("search") },
                onNavigateToChat = { navController.navigate("chat_list") },
                onNavigateToVideoDetail = { videoId -> navController.navigate("video_detail/$videoId") },
                onNavigateToEditProfile = { navController.navigate("edit_profile") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }

        composable(
            route = "video_detail/{videoId}",
            arguments = listOf(navArgument("videoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val videoId = backStackEntry.arguments?.getString("videoId") ?: "vid_101"
            VideoDetailScreen(
                videoId = videoId,
                videoDetailViewModel = videoDetailViewModel,
                currentUser = currentUser,
                onBackClick = { navController.popBackStack() },
                onChannelClick = {
                    navController.navigate("main")
                }
            )
        }

        composable("search") {
            SearchScreen(
                searchViewModel = searchViewModel,
                onBackClick = { navController.popBackStack() },
                onVideoClick = { video -> navController.navigate("video_detail/${video.id}") },
                onChannelClick = { navController.navigate("main") }
            )
        }

        composable("chat_list") {
            ChatListScreen(
                chatViewModel = chatViewModel,
                onBackClick = { navController.popBackStack() },
                onConversationClick = { navController.navigate("chat_detail") }
            )
        }

        composable("chat_detail") {
            ChatDetailScreen(
                chatViewModel = chatViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("edit_profile") {
            EditProfileScreen(
                currentUser = currentUser,
                authViewModel = authViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("settings") {
            SettingsScreen(
                settingsViewModel = settingsViewModel,
                authViewModel = authViewModel,
                onBackClick = { navController.popBackStack() },
                onSignOutClick = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
