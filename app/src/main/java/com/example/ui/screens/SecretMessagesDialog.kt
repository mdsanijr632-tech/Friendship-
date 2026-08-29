package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BanglishBadge
import com.example.ui.theme.FriendshipDeepBlue
import com.example.ui.theme.FriendshipRose

@Composable
fun SaniMessageDialog(
    messageText: String,
    onSaveMessage: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var currentText by remember(messageText) { mutableStateOf(messageText) }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.testTag("sani_message_dialog"),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "💌 ", fontSize = 22.sp)
                    Text(
                        text = "Arafat-er Jonno Sani-r Message",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = FriendshipRose
                        )
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                BanglishBadge(
                    text = "From: Sani ❤️ • To: Arafat",
                    containerColor = FriendshipRose.copy(alpha = 0.2f),
                    contentColor = FriendshipRose
                )

                if (isEditing) {
                    OutlinedTextField(
                        value = currentText,
                        onValueChange = { currentText = it },
                        label = { Text("Sani-r Message Lekho") },
                        minLines = 4,
                        maxLines = 8,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    )
                } else {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = currentText,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                lineHeight = 24.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            if (isEditing) {
                Button(
                    onClick = {
                        onSaveMessage(currentText)
                        isEditing = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FriendshipRose),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Save Koro")
                }
            } else {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = FriendshipRose),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Close")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = { isEditing = !isEditing }
            ) {
                Text(if (isEditing) "Cancel Edit" else "Edit Message ✏️")
            }
        }
    )
}

@Composable
fun ArafatMessageDialog(
    messageText: String,
    onSaveMessage: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var currentText by remember(messageText) { mutableStateOf(messageText) }
    var saveSuccess by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.testTag("arafat_message_dialog"),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "💙 ", fontSize = 22.sp)
                    Text(
                        text = "Sani-r Jonno Arafat-er Message",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = FriendshipDeepBlue
                        )
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                BanglishBadge(
                    text = "From: Arafat 💙 • To: Sani",
                    containerColor = FriendshipDeepBlue.copy(alpha = 0.2f),
                    contentColor = FriendshipDeepBlue
                )

                if (isEditing || currentText.isBlank()) {
                    OutlinedTextField(
                        value = currentText,
                        onValueChange = {
                            currentText = it
                            saveSuccess = false
                        },
                        label = { Text("Arafat, tor moner kotha lekho...") },
                        placeholder = { Text("Ore Sani, tui amr best friend...") },
                        minLines = 4,
                        maxLines = 8,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("arafat_message_input"),
                        shape = RoundedCornerShape(14.dp)
                    )
                } else {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = currentText,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                lineHeight = 24.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                if (saveSuccess) {
                    Text(
                        text = "✓ Message Saved Successfully! 💙",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00B894)
                        )
                    )
                }
            }
        },
        confirmButton = {
            if (isEditing || currentText != messageText) {
                Button(
                    onClick = {
                        onSaveMessage(currentText)
                        isEditing = false
                        saveSuccess = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FriendshipDeepBlue),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("save_arafat_msg_btn")
                ) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Save Message 💙")
                }
            } else {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = FriendshipDeepBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Close")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = { isEditing = !isEditing }
            ) {
                Text(if (isEditing) "Cancel" else "Message Lekho / Edit ✍️")
            }
        }
    )
}
