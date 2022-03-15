package com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth

import com.r3d1r4ph.mobile_lab2_itindr.database.tables.auth.AuthEntity
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken : String,
    val refreshTokenExpiredAt: String
) {
    fun toEntity() = AuthEntity(
        accessToken = accessToken,
        accessTokenExpiredAt = accessTokenExpiredAt,
        refreshToken = refreshToken,
        refreshTokenExpiredAt = refreshTokenExpiredAt,
        profile = null
    )
}
