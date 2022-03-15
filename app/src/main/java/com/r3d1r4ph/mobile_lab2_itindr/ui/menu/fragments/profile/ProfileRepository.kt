package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.profile

import android.app.Application
import androidx.lifecycle.map
import com.r3d1r4ph.mobile_lab2_itindr.domain.ProfileData
import com.r3d1r4ph.mobile_lab2_itindr.repository.auth.AuthDBRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ProfileRepository(app: Application) {
    private val authDBRepository = AuthDBRepository(app)

    fun getProfile() = authDBRepository.getProfile().map {
        if (!it.isNullOrBlank()) {
            Json.decodeFromString(it)
        } else {
            ProfileData.EMPTY
        }
    }
}