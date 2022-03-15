package com.r3d1r4ph.mobile_lab2_itindr.domain

import java.io.Serializable

@kotlinx.serialization.Serializable
data class ProfileData(
    val userId: String,
    val name: String,
    val aboutMyself: String? = null,
    val avatar: String? = null,
    val topics: List<TopicData> = listOf()
) : Serializable {
    companion object {
        val EMPTY = ProfileData(
            userId = "",
            name = "",
            aboutMyself = null,
            avatar = null,
            topics = listOf()
        )
    }
}