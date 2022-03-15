package com.r3d1r4ph.mobile_lab2_itindr.domain

data class UserData(
    val userId: String,
    val name: String,
    val aboutMyself: String?,
    val avatar: String?
) {
    companion object {
        val EMPTY = UserData(
            userId = "",
            name = "",
            aboutMyself = null,
            avatar = null
        )
    }
}