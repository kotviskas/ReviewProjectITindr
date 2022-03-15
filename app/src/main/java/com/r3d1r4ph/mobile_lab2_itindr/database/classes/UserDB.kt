package com.r3d1r4ph.mobile_lab2_itindr.database.classes

import com.r3d1r4ph.mobile_lab2_itindr.domain.UserData

data class UserDB(
    val userId: String,
    val name: String,
    val aboutMyself: String?,
    val avatar: String?
) {
    companion object {
        val EMPTY = UserDB(userId = "", name = "", aboutMyself = null, avatar = null)
    }

    fun toDomain() = UserData(
        userId = userId,
        name = name,
        aboutMyself = aboutMyself,
        avatar = avatar
    )
}