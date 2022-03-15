package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.chats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.ResultWrapper
import kotlinx.coroutines.launch

class ChatsViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = ChatsRepository(app)

    val chats = repository.observeAllChats()

    fun loadChats(onFailure: (error: String) -> Unit) {
        viewModelScope.launch {
            val response = repository.loadChatList()
            if (response is ResultWrapper.Error) {
                 response.error?.let(onFailure)
            }
        }
    }
}