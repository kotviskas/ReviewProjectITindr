package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.search

import android.app.Application
import com.r3d1r4ph.mobile_lab2_itindr.repository.user.UserDBRepository
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.user.feed.FeedService
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.user.rate.RateService
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.ResultWrapper

class SearchRepository(app: Application) {

    private val feedService = FeedService()
    private val rateService = RateService()
    private val userDBRepository = UserDBRepository(app)

    suspend fun likeUser(userId: String) = rateService.like(userId = userId)

    suspend fun dislikeUser(userId: String) = rateService.dislike(userId = userId)

    suspend fun getUserFeed() = feedService.feed().also { result ->
        if (result is ResultWrapper.Success) {
            result.value?.let { list ->
                userDBRepository.addNewUsers(list.map { it.toEntity() })
            }
        }
    }
}