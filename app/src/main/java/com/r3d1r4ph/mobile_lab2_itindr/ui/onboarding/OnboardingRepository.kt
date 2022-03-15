package com.r3d1r4ph.mobile_lab2_itindr.ui.onboarding

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.ProfileService

class OnboardingRepository {

    private val profileService = ProfileService()

    suspend fun getProfile() = profileService.getProfile()
}