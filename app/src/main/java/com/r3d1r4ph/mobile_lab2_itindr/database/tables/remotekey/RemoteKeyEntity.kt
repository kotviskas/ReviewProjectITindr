package com.r3d1r4ph.mobile_lab2_itindr.database.tables.remotekey

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class RemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    val userId: String,
    val prevKey: Int?,
    val nextKey: Int?
)
