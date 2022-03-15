package com.r3d1r4ph.mobile_lab2_itindr.ui.chat.messagerecycler

data class MessageItem(
    val messageId: String,
    val userId: String,
    val avatar: String? = null,
    val text: String? = null,
    val data: String,
    val attachments: List<String>
)
