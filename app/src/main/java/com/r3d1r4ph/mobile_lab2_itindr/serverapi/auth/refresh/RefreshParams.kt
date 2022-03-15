package com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.refresh

import kotlinx.serialization.Serializable

@Serializable
data class RefreshParams(val refreshToken: String)
