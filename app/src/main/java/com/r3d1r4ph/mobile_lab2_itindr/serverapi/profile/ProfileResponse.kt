package com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile

import com.r3d1r4ph.mobile_lab2_itindr.database.tables.user.UserEntity
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.topic.TopicResponse
import com.r3d1r4ph.mobile_lab2_itindr.domain.ProfileData
import com.r3d1r4ph.mobile_lab2_itindr.domain.TopicData
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class ProfileResponse(
    val userId: String,
    val name: String,
    val aboutMyself: String? = null,
    val avatar: String? = null,
    val topics: List<TopicResponse>
) {
    fun toDomain() = ProfileData(
        userId = userId,
        name = name,
        aboutMyself = aboutMyself,
        avatar = avatar,
        topics = topics.map { topic ->
            TopicData(
                id = topic.id,
                title = topic.title
            )
        })

    fun toEntity() = UserEntity(
        userId = userId,
        name = name,
        aboutMyself = aboutMyself,
        avatar = avatar,
        topics = Json.encodeToString(topics)
    )

}
