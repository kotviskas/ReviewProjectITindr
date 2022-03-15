package com.r3d1r4ph.mobile_lab2_itindr.domain

data class AuthData(
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String,
    val profile: String
)
