package com.example.ui.screens.shorts

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.User
import com.example.ui.components.CommentItem
import com.example.ui.components.ShortCardItem
import com.example.ui.theme.DekkhoRed
import com.example.ui.viewmodel.ShortsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShortsScreen(
    shortsViewModel: ShortsViewModel,
    currentUser: User?,
    onChannelClick: (String) -> Unit
) {
    val shortsList by shortsViewModel.shortsList.collectAsState()
    val currentIndex by shortsViewModel.currentIndex.collectAsState()
    val showCommentsSheet by shortsViewModel.showCommentsSheet.collectAsState()
    val activeComments by shortsViewModel.activeShortComments.collectAsState()
    val context = LocalContext.current

    var newCommentText by remember { mutableStateOf("") }
    val currentShort = shortsList.getOrNull(currentIndex) ?: shortsList.firstOrNull()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .testTag("shorts_screen")
    ) {
        if (currentShort != null) {
            ShortCardItem(
                short = currentShort,
                onLikeClick = { shortsViewModel.toggleLikeShort(currentShort.id) },
                onCommentClick = { shortsViewModel.openComments(currentShort.id) },
                onShareClick = {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Watch this Short on DekkhoTube: ${currentShort.title}\nhttps://dekkhotube.app/s/${currentShort.id}")
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(sendIntent, "Share Short"))
                },
                onChannelClick = onChannelClick
            )
        }

        // Interactive Comments Bottom Sheet Modal
        if (showCommentsSheet && currentShort != null) {
            ModalBottomSheet(
                onDismissRequest = { shortsViewModel.closeComments() },
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                        .padding(horizontal = 16.dp)
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Comments (${activeComments.size})",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { shortsViewModel.closeComments() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // Comments List
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(activeComments) { comment ->
                            CommentItem(comment = comment)
                        }
                    }

                    // Comment Input Box
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = newCommentText,
                            onValueChange = { newCommentText = it },
                            placeholder = { Text("Add a comment...") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("short_comment_input"),
                            shape = RoundedCornerShape(24.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = DekkhoRed,
                                unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            singleLine = true
                        )

                        IconButton(
                            onClick = {
                                if (currentUser != null && newCommentText.isNotBlank()) {
                                    shortsViewModel.addShortComment(currentShort.id, newCommentText, currentUser)
                                    newCommentText = ""
                                }
                            },
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .background(DekkhoRed, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
