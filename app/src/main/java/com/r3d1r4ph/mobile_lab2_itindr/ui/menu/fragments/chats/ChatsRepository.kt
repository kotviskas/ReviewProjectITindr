package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.chats

import android.app.Application
import androidx.lifecycle.map
import androidx.room.withTransaction
import com.r3d1r4ph.mobile_lab2_itindr.database.Database
import com.r3d1r4ph.mobile_lab2_itindr.repository.chat.ChatDBRepository
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.chat.ChatService
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.ResultWrapper

class ChatsRepository(app: Application) {

    private val chatService = ChatService()
    private val chatDBRepository = ChatDBRepository(app)
    private val appDatabase = Database.getInstance(app)

    fun observeAllChats() =
        chatDBRepository.observeAllChats().map { list -> list.map { it.toDomain() } }

    suspend fun loadChatList() = chatService.getChat().also { result ->
        if (result is ResultWrapper.Success) {
            result.value?.let { list ->
                appDatabase.withTransaction {
                    chatDBRepository.deleteAllChats()
                    chatDBRepository.addChatList(list.map { it.toEntity() })
                }
            }
        }
    }
}