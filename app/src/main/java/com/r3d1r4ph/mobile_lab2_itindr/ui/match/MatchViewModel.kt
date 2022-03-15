package com.r3d1r4ph.mobile_lab2_itindr.ui.match

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.ResultWrapper
import kotlinx.coroutines.launch

class MatchViewModel(app: Application, arguments: Bundle?) : AndroidViewModel(app) {

    companion object {
        const val USER_ID_KEY = "User ID Key"
    }

    private val repository = MatchRepository(app)

    private val _goToChatScreen = MutableLiveData<Boolean>()
    val goToChatScreen: LiveData<Boolean>
        get() = _goToChatScreen

    private var userId = ""
    var newChatId = ""
        private set

    init {
        userId = arguments?.getString(USER_ID_KEY).toString()
    }

    fun createNewChat(onFailure: (error: String) -> Unit) {
        viewModelScope.launch {
            when (val response = repository.createNewChat(userId = userId)) {
                is ResultWrapper.Success -> {
                    response.value?.let {
                        newChatId = it.id
                        _goToChatScreen.value = true
                    }
                }
                is ResultWrapper.Error -> response.error?.let(onFailure)
            }
        }
    }
}