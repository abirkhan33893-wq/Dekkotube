package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.User
import com.example.ui.theme.DekkhoRed

@Composable
fun DekkhoTopBar(
    currentUser: User?,
    unreadNotifications: Int = 2,
    unreadMessages: Int = 1,
    onSearchClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onChatClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // App Logo Icon
        Box(
            modifier = Modifier
                .size(34.dp)
                .background(DekkhoRed, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Brand Title
        Text(
            text = "DekkhoTube",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                letterSpacing = (-0.5).sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.weight(1f))

        // Search Action
        IconButton(
            onClick = onSearchClick,
            modifier = Modifier.testTag("top_bar_search_button")
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        // Notifications Action
        IconButton(
            onClick = onNotificationsClick,
            modifier = Modifier.testTag("top_bar_notifications_button")
        ) {
            BadgedBox(
                badge = {
                    if (unreadNotifications > 0) {
                        Badge(containerColor = DekkhoRed) {
                            Text(text = unreadNotifications.toString(), color = Color.White)
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.NotificationsNone,
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Chat Action
        IconButton(
            onClick = onChatClick,
            modifier = Modifier.testTag("top_bar_chat_button")
        ) {
            BadgedBox(
                badge = {
                    if (unreadMessages > 0) {
                        Badge(containerColor = DekkhoRed) {
                            Text(text = unreadMessages.toString(), color = Color.White)
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ChatBubbleOutline,
                    contentDescription = "Messages",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.width(4.dp))

        // Profile Avatar
        AsyncImage(
            model = currentUser?.avatarUrl?.ifEmpty { "https://picsum.photos/seed/alex/100/100" }
                ?: "https://picsum.photos/seed/alex/100/100",
            contentDescription = "Profile",
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .clickable { onProfileClick() }
                .testTag("top_bar_profile_avatar")
        )
    }
}
