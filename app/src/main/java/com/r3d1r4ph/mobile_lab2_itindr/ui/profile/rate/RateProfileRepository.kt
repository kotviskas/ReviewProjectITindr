package com.r3d1r4ph.mobile_lab2_itindr.ui.profile.rate

import android.app.Application
import com.r3d1r4ph.mobile_lab2_itindr.repository.user.UserDBRepository
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.user.rate.RateService

class RateProfileRepository(app: Application) {

    private val userDBRepository = UserDBRepository(app)
    private val rateService = RateService()

    suspend fun getUserById(userId: String) = userDBRepository.getUserById(userId)

    suspend fun likeUser(userId: String) = rateService.like(userId = userId)

    suspend fun dislikeUser(userId: String) = rateService.dislike(userId = userId)
}