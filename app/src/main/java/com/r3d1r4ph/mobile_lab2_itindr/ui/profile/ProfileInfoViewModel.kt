package com.r3d1r4ph.mobile_lab2_itindr.ui.profile

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.ProfileParams
import com.r3d1r4ph.mobile_lab2_itindr.utils.GeneralUtils
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.ResultWrapper
import kotlinx.coroutines.launch

class ProfileInfoViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = ProfileInfoRepository(app)

    val topics = repository.observeTopics()
    val profileData = repository.getProfile()

    private val _readyToObserveProfile = MutableLiveData<Boolean>()
    val readyToObserveProfile: LiveData<Boolean>
        get() = _readyToObserveProfile

    fun updateTopics(onFailure: (error: String) -> Unit) {
        viewModelScope.launch {
            when (val response = repository.getTopics()) {
                is ResultWrapper.Success -> {
                    response.value?.let { list ->
                        repository.updateTopics(list)
                        _readyToObserveProfile.value = true
                    }
                }
                is ResultWrapper.Error -> response.error?.let(onFailure)
            }
        }
    }

    fun saveProfile(
        profileParams: ProfileParams,
        avatarUri: Uri?,
        context: Context,
        onFailure: (error: String) -> Unit
    ) {
        if (avatarUri != null) {

            if (avatarUri.toString().contains("http")) {
                sendProfileDataToServer(profileParams, onFailure)
                return
            }

            viewModelScope.launch {
                repository.postAvatar(
                    multipartBodyPart = GeneralUtils.multipartBodyPartFromUri(
                        context,
                        avatarUri
                    )
                )
            }
        } else {
            viewModelScope.launch {
                repository.deleteAvatar()
            }
        }
        sendProfileDataToServer(profileParams, onFailure)
    }

    private fun sendProfileDataToServer(
        profileParams: ProfileParams,
        onFailure: (error: String) -> Unit
    ) {
        viewModelScope.launch {
            when (val response = repository.sendProfile(profileParams = profileParams)) {
                is ResultWrapper.Success -> {
                    response.value?.let {
                        repository.updateProfile(it)
                    }
                }
                is ResultWrapper.Error -> response.error?.let(onFailure)
            }
        }
    }
}