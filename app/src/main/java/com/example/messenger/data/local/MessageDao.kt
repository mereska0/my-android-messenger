package com.example.messenger.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages WHERE chatId = :chatId ORDER BY timestamp ASC")
    fun getMessages(chatId: Int): Flow<List<MessageEntity>>

    @Insert
    suspend fun insertMessage(message: MessageEntity)

    @Delete
    suspend fun deleteMessage(message: MessageEntity)

    @Query("UPDATE messages SET text = :newText WHERE id = :id")
    suspend fun updateMessage(id: Int, newText: String)

    @Query("""
        SELECT * FROM messages 
        WHERE chatId = :chatId 
        ORDER BY timestamp DESC 
        LIMIT 1
    """)
    fun getLastMessage(chatId: Int): Flow<MessageEntity?>

    @Query("SELECT * FROM messages WHERE id = :id")
    fun getMessageById(id: Int): Flow<MessageEntity>
}
