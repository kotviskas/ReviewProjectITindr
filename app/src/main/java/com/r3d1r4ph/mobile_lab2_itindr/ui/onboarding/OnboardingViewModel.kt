package com.r3d1r4ph.mobile_lab2_itindr.ui.onboarding

import android.app.Application
import android.content.Intent
import androidx.lifecycle.*
import com.r3d1r4ph.mobile_lab2_itindr.repository.auth.AuthDBRepository
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.Network
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.ProfileService
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.ResultWrapper
import kotlinx.coroutines.launch

class OnboardingViewModel(app: Application, intent: Intent) : AndroidViewModel(app) {

    companion object {
        const val CHECK_AUTHORIZATION = "Check authorization"
    }

    private val repository = OnboardingRepository()

    private var checkAuth = true

    private val _authorized = MutableLiveData<Boolean>()
    val authorized: LiveData<Boolean>
        get() = _authorized

    init {
        Network.authDBRepository = AuthDBRepository(app)
        checkAuth = intent.getBooleanExtra(CHECK_AUTHORIZATION, true)
    }

    fun checkAuthorization(onFailure: (error: String) -> Unit) {
        if (Network.transferAccessToken() != "Bearer null" && checkAuth) {
            viewModelScope.launch {
                when (val response = repository.getProfile()) {
                    is ResultWrapper.Success -> {
                        _authorized.value = true
                    }
                    is ResultWrapper.Error -> response.error?.let(onFailure)
                }
            }
        }
    }
}