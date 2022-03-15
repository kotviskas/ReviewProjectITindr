package com.r3d1r4ph.mobile_lab2_itindr.database.tables.auth

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth")
data class AuthEntity(
    @PrimaryKey(autoGenerate = false)
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String,
     val profile: String?
)
