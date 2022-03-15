package com.r3d1r4ph.mobile_lab2_itindr.domain

data class MessageData(
    val id: String,
    val text: String?,
    val createdAt: String,
    val user: UserData,
    val attachments: List<String>
) {
    companion object {
        val EMPTY = MessageData(
            id = "",
            text = null,
            createdAt = "",
            user = UserData.EMPTY,
            attachments = listOf()
        )
    }
}
