package com.r3d1r4ph.mobile_lab2_itindr.ui.profile.rate

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.mobile_lab2_itindr.domain.ProfileData
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.ResultWrapper
import kotlinx.coroutines.launch

class RateProfileViewModel(app: Application, intent: Intent) : AndroidViewModel(app) {

    companion object {
        const val USER_ID_KEY = "User ID key"
    }

    private val repository = RateProfileRepository(app)

    var userId = ""
        private set

    private val _userData = MutableLiveData<ProfileData>()
    val userData: LiveData<ProfileData>
        get() = _userData

    private val _likeIsMutual = MutableLiveData<Boolean>()
    val likeIsMutual: LiveData<Boolean>
        get() = _likeIsMutual

    init {
        userId = intent.getStringExtra(USER_ID_KEY).toString()
        getUserProfileById(userId)
    }

    private fun getUserProfileById(userId: String) {
        viewModelScope.launch {
            _userData.value = repository.getUserById(userId)
        }
    }

    fun likeUser(onFailure: (error: String) -> Unit) {
        viewModelScope.launch {
            val response =
                repository.likeUser(userId = userId)
            when (response) {
                is ResultWrapper.Success -> response.value?.let {
                    _likeIsMutual.value = it.isMutual
                }
                is ResultWrapper.Error -> response.error?.let(onFailure)
            }
        }
    }

    fun dislikeUser(onFailure: (error: String) -> Unit, onResponse: () -> Unit) {
        viewModelScope.launch {
            val response =
                repository.dislikeUser(userId = userId)
            if (response is ResultWrapper.Error) {
                response.error?.let(onFailure)
            }
            onResponse.invoke()
        }
    }
}