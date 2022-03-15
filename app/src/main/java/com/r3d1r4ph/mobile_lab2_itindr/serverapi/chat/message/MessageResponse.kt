package com.r3d1r4ph.mobile_lab2_itindr.serverapi.chat.message

import com.r3d1r4ph.mobile_lab2_itindr.database.tables.message.MessageEntity
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.ProfileResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class MessageResponse(
    val id: String,
    val text: String? = null,
    val createdAt: String,
    val user: UserResponse,
    val attachments: List<String>
) {
    fun toEntity() = MessageEntity(
        id = id,
        text = text,
        createdAt = createdAt,
        user = user.toDBClass(),
        attachments = Json.encodeToString(attachments)
    )
}
