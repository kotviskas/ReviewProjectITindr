package com.r3d1r4ph.mobile_lab2_itindr.ui.match

import android.app.Application
import com.r3d1r4ph.mobile_lab2_itindr.repository.chat.ChatDBRepository
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.chat.ChatParams
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.chat.ChatService
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.ResultWrapper

class MatchRepository(app: Application) {

    private val chatService = ChatService()
    private val chatDBRepository = ChatDBRepository(app)

    suspend fun createNewChat(userId: String) =
        chatService.postChat(chatParams = ChatParams(userId = userId)).also { result ->
            if (result is ResultWrapper.Success) {
                result.value?.let { chat ->
                    chatDBRepository.addChat(chat.toEntity())
                }
            }
        }
}