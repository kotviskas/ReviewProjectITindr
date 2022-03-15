package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.chats.chatrecycler

data class ChatItem(
    val id: String,
    val username: String,
    val lastMessage: String? = null,
    val avatar: String? = null
)