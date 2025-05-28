package io.cyberfan.cyberchat.message

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.cyberfan.cyberchat.Injection
import io.cyberfan.cyberchat.data.ChatRepository
import io.cyberfan.cyberchat.model.Chat
import io.cyberfan.cyberchat.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatId: String,
    private val user: String
) : ViewModel() {

    val repository: ChatRepository = Injection.repository

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _chatInfo = MutableStateFlow(Chat("", "", emptyList(), 0))
    val chatInfo = _chatInfo.asStateFlow()

    init {
        loadChatInfo()
        observeMessages()
    }

    private fun loadChatInfo() {
        viewModelScope.launch {
            repository.getChatInfo(chatId)
                .onSuccess { chat ->
                    _chatInfo.value = chat
                }
                .onFailure { exception ->
                    Log.e("TAG", "loadChatInfo: error", exception)
                }
        }
    }

    private fun observeMessages() {
        viewModelScope.launch {
            repository.getMessages(chatId)
                .catch { e ->
                    Log.e("TAG", "observeMessages: ", e)
                }
                .collect { messagesList ->
                    _messages.value = messagesList.map {
                        it.copy(isFromCurrentUser = it.sender == user)
                    }
                }
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            val message = Message(
                text = text,
                sender = user,
                chatId = chatId,
                timestamp = System.currentTimeMillis()
            )

            repository.sendMessage(message)
                .onFailure { exception ->
                    Log.e("TAG", "sendMessage: ${exception.message}", exception)
                }
        }
    }

    fun exitChat() {
        viewModelScope.launch {
            repository.exitChat(chatId, user)
                .onFailure { exception ->
                    Log.e("TAG", "exitChat: ${exception.message}", exception)
                }
        }
    }
}
