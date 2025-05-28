package io.cyberfan.cyberchat.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.cyberfan.cyberchat.Injection
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {
    private val repository = Injection.repository

    private val _loginEvent = Channel<LoginUiEvent>()
    val events = _loginEvent.receiveAsFlow()

    fun joinChat(chatId: String, userName: String) {
        if (chatId.trim().isBlank() || userName.trim().isBlank()) return

        viewModelScope.launch {
            val result = repository.joinChat(chatId, userName)
            if (result.isSuccess) {
                _loginEvent.send(LoginUiEvent.Success(chatId, userName))
            } else {
                _loginEvent.send(
                    LoginUiEvent.Failure(
                        result.exceptionOrNull()?.message ?: "Erro desconhecido"
                    )
                )
            }
        }
    }
}

sealed class LoginUiEvent {
    data class Success(val chatId: String, val userName: String) : LoginUiEvent()
    data class Failure(val message: String) : LoginUiEvent()
}
