package com.r3d1r4ph.mobile_lab2_itindr.serverapi.chat

import com.r3d1r4ph.mobile_lab2_itindr.database.tables.chat.ChatEntity
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.chat.message.MessageResponse
import kotlinx.serialization.Serializable

@Serializable
data class ChatListResponse(val chat: ChatResponse, val lastMessage: MessageResponse? = null) {
    fun toEntity() = ChatEntity(
        chat = chat.toDBClass(),
        lastMessage = lastMessage?.toEntity()
    )
}
