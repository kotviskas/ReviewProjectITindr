package com.r3d1r4ph.mobile_lab2_itindr.serverapi.user.rate

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.Network
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.getWrappedResult

class RateService {
    private val api = Network.retrofit.create(RateInterface::class.java)

    suspend fun like(userId: String) = getWrappedResult(suspend { api.like(userId = userId) })

    suspend fun dislike(userId: String) = getWrappedResult(suspend { api.dislike(userId = userId) })
}