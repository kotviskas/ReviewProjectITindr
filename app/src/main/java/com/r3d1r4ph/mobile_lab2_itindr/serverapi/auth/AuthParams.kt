package com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthParams(
    val email: String,
    val password: String
)