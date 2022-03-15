package com.r3d1r4ph.mobile_lab2_itindr.serverapi.user.feed

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.Network
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.ProfileResponse
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.getWrappedResult
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedService() {
    private val api = Network.retrofit.create(FeedInterface::class.java)

    suspend fun feed() = getWrappedResult { api.feed() }
}