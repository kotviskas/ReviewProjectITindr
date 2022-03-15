package com.r3d1r4ph.mobile_lab2_itindr.serverapi.topic

import com.r3d1r4ph.mobile_lab2_itindr.database.tables.topic.TopicEntity
import kotlinx.serialization.Serializable

@Serializable
data class TopicResponse(val id: String, val title: String) : java.io.Serializable {
    fun toEntity() = TopicEntity(
        id = id,
        title = title
    )
}
