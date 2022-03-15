package com.r3d1r4ph.mobile_lab2_itindr.ui.aboutuser

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.mobile_lab2_itindr.domain.ProfileData
import com.r3d1r4ph.mobile_lab2_itindr.ui.profile.rate.RateProfileViewModel
import kotlinx.coroutines.launch

class AboutUserViewModel(app: Application, arguments: Bundle?) : AndroidViewModel(app) {

    companion object {
        const val USER_ID_KEY = "User ID key"
    }

    private val repository = AboutUserRepository(app)

    private var userId = ""

    private val _userData = MutableLiveData<ProfileData>()
    val userData: LiveData<ProfileData>
        get() = _userData

    init {
        userId = arguments?.getString(RateProfileViewModel.USER_ID_KEY).toString()
        getUserProfileById(userId)
    }

    private fun getUserProfileById(userId: String) {
        viewModelScope.launch {
            _userData.value = repository.getUserById(userId = userId)
        }
    }
}