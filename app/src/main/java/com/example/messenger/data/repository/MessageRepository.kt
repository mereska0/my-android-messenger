package com.example.messenger.data.repository

import com.example.messenger.data.local.MessageDao
import com.example.messenger.data.local.MessageEntity
import kotlinx.coroutines.flow.Flow

class MessageRepository(
    private val dao: MessageDao
) {

    fun getMessages(chatId: Int): Flow<List<MessageEntity>> {
        return dao.getMessages(chatId)
    }

    suspend fun sendMessage(message: MessageEntity) {
        dao.insertMessage(message)
    }

    suspend fun deleteMessage(message: MessageEntity) {
        dao.deleteMessage(message)
    }

    suspend fun editMessage(id: Int, newText: String) {
        dao.updateMessage(id, newText)
    }

    fun getLastMessage(chatId: Int) =
        dao.getLastMessage(chatId)

    fun getMessageById(id: Int): Flow<MessageEntity> {
        return dao.getMessageById(id)
    }
}