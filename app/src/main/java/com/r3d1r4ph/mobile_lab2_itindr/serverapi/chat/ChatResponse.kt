package com.r3d1r4ph.mobile_lab2_itindr.serverapi.chat

import com.r3d1r4ph.mobile_lab2_itindr.database.classes.ChatDB
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.chat.ChatEntity
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.message.MessageEntity
import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(val id: String, val title: String, val avatar: String? = null) {
    fun toEntity() = ChatEntity(
        chat = ChatDB(id = id, title = title, avatar = avatar),
        lastMessage = MessageEntity.EMPTY
    )

    fun toDBClass() = ChatDB(
        id = id,
        title = title,
        avatar = avatar
    )
}