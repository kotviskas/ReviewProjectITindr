package com.r3d1r4ph.mobile_lab2_itindr.ui.chat

import android.app.Application
import com.r3d1r4ph.mobile_lab2_itindr.repository.chat.ChatDBRepository

class ChatRepository(app: Application) {

    private val chatDBRepository = ChatDBRepository(app)
    //todo
    suspend fun getChatLabelById(chatId: String) =
        chatDBRepository.getChatById(chatId = chatId).toDomain().toLabel()
}