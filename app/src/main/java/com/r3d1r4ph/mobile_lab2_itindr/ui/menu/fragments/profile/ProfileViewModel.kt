package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = ProfileRepository(app)

    val profileData = repository.getProfile()
}