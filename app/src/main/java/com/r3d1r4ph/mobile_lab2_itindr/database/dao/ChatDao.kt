package com.r3d1r4ph.mobile_lab2_itindr.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.r3d1r4ph.mobile_lab2_itindr.database.classes.ChatDB
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.chat.ChatEntity

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chatEntity: ChatEntity)

    @Query("SELECT * FROM chat WHERE chat_id = :chatId")
    suspend fun getChatLabelById(chatId: String): ChatEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(chats: List<ChatEntity>)

    @Query("DELETE FROM chat")
    suspend fun deleteAll()

    @Query("SELECT * FROM chat ORDER BY message_createdAt ASC")
    fun observeAll(): LiveData<List<ChatEntity>>
}