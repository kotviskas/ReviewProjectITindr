package com.r3d1r4ph.mobile_lab2_itindr.database.tables.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.r3d1r4ph.mobile_lab2_itindr.domain.ProfileData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val userId: String,
    val name: String,
    val aboutMyself: String?,
    val avatar: String?,
    val topics: String
) {
    fun toDomain() = ProfileData(
        userId = userId,
        name = name,
        aboutMyself = aboutMyself,
        avatar = avatar,
        topics = Json.decodeFromString(topics)
    )
}
