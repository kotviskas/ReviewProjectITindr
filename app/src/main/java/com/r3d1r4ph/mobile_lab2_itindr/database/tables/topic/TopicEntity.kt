package com.r3d1r4ph.mobile_lab2_itindr.database.tables.topic

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.r3d1r4ph.mobile_lab2_itindr.domain.TopicData

@Entity(tableName = "topic")
data class TopicEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String
) {
    fun toDomain() = TopicData(
        id = id,
        title = title
    )
}