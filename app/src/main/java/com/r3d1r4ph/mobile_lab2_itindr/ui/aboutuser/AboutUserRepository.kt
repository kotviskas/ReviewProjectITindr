package com.r3d1r4ph.mobile_lab2_itindr.ui.aboutuser

import android.app.Application
import com.r3d1r4ph.mobile_lab2_itindr.repository.user.UserDBRepository

class AboutUserRepository(app: Application) {

    private val userDBRepository = UserDBRepository(app)

    suspend fun getUserById(userId: String) = userDBRepository.getUserById(userId = userId)
}