package com.r3d1r4ph.mobile_lab2_itindr.serverapi.user

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.Network
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.getWrappedResult

class UserService {
    private val api = Network.retrofit.create(UserInterface::class.java)

    suspend fun user(limit: Int, offset: Int) =
        getWrappedResult(suspend { api.user(limit = limit, offset = offset) })
}