package io.cyberfan.cyberchat.model

data class Chat(
    val id: String = "",
    val name: String = "",
    val participants: List<String> = emptyList(),
    val participantCount: Int,
)
