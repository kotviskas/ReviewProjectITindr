package com.r3d1r4ph.mobile_lab2_itindr.database.tables.message

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.r3d1r4ph.mobile_lab2_itindr.database.classes.UserDB

@Entity(tableName = "message")
data class MessageEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val text: String?,
    val createdAt: String,
    @Embedded(prefix = "message_") val user: UserDB,
    val attachments: String
) {
    companion object {
        val EMPTY = MessageEntity(
            id = "",
            text = null,
            createdAt = "",
            user = UserDB.EMPTY,
            attachments = ""
        )
    }
}