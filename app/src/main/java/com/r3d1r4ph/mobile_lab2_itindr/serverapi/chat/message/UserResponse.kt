package com.r3d1r4ph.mobile_lab2_itindr.serverapi.chat.message

import com.r3d1r4ph.mobile_lab2_itindr.database.classes.UserDB
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val userId: String,
    val name: String,
    val aboutMyself: String? = null,
    val avatar: String? = null
) {
    fun toDBClass() = UserDB(
        userId = userId,
        name = name,
        aboutMyself = aboutMyself,
        avatar = avatar
    )
}
