package io.cyberfan.cyberchat

import io.cyberfan.cyberchat.data.ChatRepository
import io.cyberfan.cyberchat.data.FirebaseChatRepository

object Injection {
    val repository: ChatRepository by lazy { FirebaseChatRepository() }
}