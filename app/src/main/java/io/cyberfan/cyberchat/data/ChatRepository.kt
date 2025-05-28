package io.cyberfan.cyberchat.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import io.cyberfan.cyberchat.model.Chat
import io.cyberfan.cyberchat.model.Message
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

interface ChatRepository {
    suspend fun sendMessage(message: Message): Result<Unit>
    suspend fun getMessages(chatId: String): Flow<List<Message>>
    suspend fun getChatInfo(chatId: String): Result<Chat>
    suspend fun joinChat(chatId: String, user: String): Result<Unit>
    suspend fun exitChat(chatId: String, user: String): Result<Unit>
}

class FirebaseChatRepository : ChatRepository {
    private val database = FirebaseDatabase.getInstance()
    private val chatsRef = database.getReference("chats")

    override suspend fun sendMessage(message: Message): Result<Unit> = try {
        val messageId = chatsRef.child(message.chatId).child("messages").push().key ?: ""

        val messageWithId = message.copy(id = messageId)

        // Salvar mensagem em chats/{chatId}/messages/{messageId}
        chatsRef.child(message.chatId)
            .child("messages")
            .child(messageId)
            .setValue(messageWithId)
            .await()

        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getMessages(chatId: String): Flow<List<Message>> = callbackFlow {
        val messagesRef = chatsRef.child(chatId).child("messages")

        val listener = messagesRef.orderByChild("timestamp")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages = mutableListOf<Message>()

                    snapshot.children.forEach { messageSnapshot ->
                        messageSnapshot.getValue<Message>()?.let { message ->
                            messages.add(message)
                        }
                    }

                    trySend(messages.sortedBy { it.timestamp })
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            })

        awaitClose { messagesRef.removeEventListener(listener) }
    }

    override suspend fun getChatInfo(chatId: String): Result<Chat> {
        return try {
            val chatSnapshot = chatsRef.child(chatId).get().await()

            if (chatSnapshot.exists()) {
                val chatName = chatSnapshot.child("name").getValue<String>() ?: ""
                val participants = chatSnapshot.child("participants")
                    .getValue<List<String>>().orEmpty()

                val chatInfo = Chat(
                    id = chatId,
                    name = chatName,
                    participantCount = participants.size,
                    participants = participants
                )

                Result.success(chatInfo)
            } else {
                Result.failure(Exception("Chat não encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun joinChat(chatId: String, user: String): Result<Unit> {
        return try {
            val chatSnapshot = chatsRef.child(chatId).get().await()

            if (chatSnapshot.exists()) {
                val participants = chatSnapshot.child("participants")
                    .getValue<List<String>>().orEmpty()

                if (!participants.contains(user)) {
                    val updatedParticipants = participants + user
                    chatsRef.child(chatId)
                        .child("participants")
                        .setValue(updatedParticipants)
                        .await()
                }
                Result.success(Unit)
            } else {
                Result.failure(Exception("Chat não encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun exitChat(chatId: String, user: String): Result<Unit> {
        return try {
            val chatSnapshot = chatsRef.child(chatId).get().await()

            if (chatSnapshot.exists()) {
                val participants = chatSnapshot.child("participants")
                    .getValue<List<String>>().orEmpty()

                if (participants.contains(user)) {
                    val updatedParticipants = participants - user
                    chatsRef.child(chatId)
                        .child("participants")
                        .setValue(updatedParticipants)
                        .await()
                }
                Result.success(Unit)
            } else {
                Result.failure(Exception("Chat não encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
