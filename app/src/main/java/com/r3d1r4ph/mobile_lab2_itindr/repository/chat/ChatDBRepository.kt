package com.r3d1r4ph.mobile_lab2_itindr.repository.chat

import android.app.Application
import androidx.lifecycle.LiveData
import com.r3d1r4ph.mobile_lab2_itindr.database.Database
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.chat.ChatEntity

class ChatDBRepository(app: Application) {
    private val chatDao = Database.getInstance(app).getChatDao()

    suspend fun addChat(chat: ChatEntity) {
        chatDao.insert(chat)
    }

    fun observeAllChats(): LiveData<List<ChatEntity>> = chatDao.observeAll()

    suspend fun deleteAllChats() = chatDao.deleteAll()

    suspend fun addChatList(chats: List<ChatEntity>) = chatDao.insertAll(chats)

    suspend fun getChatById(chatId: String) = chatDao.getChatLabelById(chatId = chatId)
}