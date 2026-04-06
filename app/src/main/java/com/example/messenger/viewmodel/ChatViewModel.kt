package com.example.messenger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.local.MessageEntity
import com.example.messenger.data.repository.Graph
import com.example.messenger.data.repository.MessageRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ChatViewModel : ViewModel() {

    private val repo = MessageRepository(
        Graph.database.messageDao()
    )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getMessages(chatId: Int): Flow<List<MessageEntity>> =
        repo.getMessages(chatId)

    fun getLastMessage(chatId: Int): Flow<MessageEntity?> =
        repo.getLastMessage(chatId)

    fun searchChats(chats: Flow<List<MessageEntity>>): Flow<List<MessageEntity>> {
        return combine(chats, searchQuery) { list, query ->
            if (query.isBlank()) {
                list
            } else {
                list.filter {
                    it.text.contains(query, ignoreCase = true)
                }
            }
        }
    }

    fun sendMessage(chatId: Int, text: String, isFromMe: Boolean) {
        viewModelScope.launch {
            repo.sendMessage(
                MessageEntity(
                    chatId = chatId,
                    text = text,
                    isFromMe = isFromMe,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    fun deleteMessage(message: MessageEntity) {
        viewModelScope.launch {
            repo.deleteMessage(message)
        }
    }

    fun editMessage(id: Int, newText: String) {
        viewModelScope.launch {
            repo.editMessage(id, newText)
        }
    }

    fun getMessageById(id: Int) =
        repo.getMessageById(id)

    fun formatTime(timestamp: Long): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault())
            .format(Date(timestamp))
    }
}