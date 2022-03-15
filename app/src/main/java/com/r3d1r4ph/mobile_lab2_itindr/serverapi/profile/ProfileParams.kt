package com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile

import kotlinx.serialization.Serializable

@Serializable
data class ProfileParams(
    val name: String,
    val aboutMyself: String? = null,
    val topics: List<String>? = null
)
