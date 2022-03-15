package com.r3d1r4ph.mobile_lab2_itindr.serverapi.user.rate

import com.r3d1r4ph.mobile_lab2_itindr.domain.LikeData
import kotlinx.serialization.Serializable

@Serializable
data class LikeResponse(val isMutual: Boolean) {
    fun toDomain() = LikeData(isMutual = isMutual)
}
