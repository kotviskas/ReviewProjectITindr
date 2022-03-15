package com.r3d1r4ph.mobile_lab2_itindr.database.tables.chat

import androidx.room.Embedded
import androidx.room.Entity
import com.r3d1r4ph.mobile_lab2_itindr.database.classes.ChatDB
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.message.MessageEntity
import com.r3d1r4ph.mobile_lab2_itindr.domain.ChatData
import com.r3d1r4ph.mobile_lab2_itindr.domain.ChatLabelData
import com.r3d1r4ph.mobile_lab2_itindr.domain.MessageData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Entity(tableName = "chat", primaryKeys = ["chat_id"])
data class ChatEntity(
    @Embedded(prefix = "chat_") val chat: ChatDB,
    @Embedded(prefix = "message_") val lastMessage: MessageEntity?
) {
    fun toDomain() = ChatData(
        chat = ChatLabelData(
            id = chat.id,
            title = chat.title,
            avatar = chat.avatar
        ),
        lastMessage = if (lastMessage != null) MessageData(
            id = lastMessage.id,
            text = lastMessage.text,
            createdAt = lastMessage.createdAt,
            user = lastMessage.user.toDomain(),
            attachments = Json.decodeFromString(lastMessage.attachments)
        ) else MessageData.EMPTY
    )
}
