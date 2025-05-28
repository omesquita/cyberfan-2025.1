package io.cyberfan.cyberchat.model

data class Message(
    val id: String = "",
    val text: String = "",
    val sender: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val chatId: String = "",
    val isFromCurrentUser: Boolean = false
)
