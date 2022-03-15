package com.r3d1r4ph.mobile_lab2_itindr.domain

import com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.chats.chatrecycler.ChatItem

data class ChatData(
    val chat: ChatLabelData,
    val lastMessage: MessageData
) {
    fun toLabel() = ChatLabelData(
        id = chat.id,
        title = chat.title,
        avatar = chat.avatar
    )

    fun toChatItem() = ChatItem(
        id = chat.id,
        username = chat.title,
        lastMessage = lastMessage.text,
        avatar = chat.avatar
    )
}