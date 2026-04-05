package com.example.messenger.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatViewModel: ViewModel() {
    private val _chats = List(40){
        Message(it,
            (0..19).random(),
            (100..200).random().toString(),
            (0..1).random() != 0
        )
    }
    val chats = mutableStateListOf<Message>().apply {
        addAll(_chats)
    }
    fun sendMessage(chatId: Int, text: String, isFromMe: Boolean){
        val newId = (chats.maxOfOrNull { it.id } ?: 0) + 1
        chats.add(Message(newId, chatId, text, isFromMe))
        updateContacts()
    }
    var contacts by mutableStateOf<List<Chat>>(emptyList())
        private set
    private fun updateContacts() {
        contacts = buildContacts()
    }
    init {
        contacts = buildContacts()
    }
    private fun buildContacts(): List<Chat> {
        return (0..19).map { chatId ->
            val lastMessage = chats
                .filter { it.chatId == chatId }
                .lastOrNull()?.text ?: "No messages"

            Chat(chatId, "chat$chatId", lastMessage)
        }
    }
    fun deleteMessage(id: Int) {
        chats.removeAll { it.id == id }
        updateContacts()
    }

    var searchQuery by mutableStateOf("")
    fun updateSearch(query: String){
        searchQuery = query
    }

    fun editMessage(id: Int, newText: String) {
        val index = chats.indexOfFirst { it.id == id }
        if (index != -1) {
            chats[index] = chats[index].copy(text = newText)
            updateContacts()
        }
    }
    fun formatTime(time: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(time))
    }




    val filteredContacts: List<Chat>
        get() = contacts.filter {
            it.name.contains(searchQuery, ignoreCase = true)
        }
}
data class Chat(
    val id: Int,
    val name: String,
    val lastMessage: String
)
data class Message(
    val id: Int,
    val chatId: Int,
    val text: String,
    val isFromMe: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)