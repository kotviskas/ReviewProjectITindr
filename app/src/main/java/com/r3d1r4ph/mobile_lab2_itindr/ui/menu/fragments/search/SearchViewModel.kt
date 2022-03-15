package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.search

import android.app.Application
import androidx.lifecycle.*
import com.r3d1r4ph.mobile_lab2_itindr.domain.ProfileData
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.ResultWrapper
import kotlinx.coroutines.launch
import java.util.*

class SearchViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = SearchRepository(app)

    private val _likeIsMutual = MutableLiveData<Boolean>()
    val likeIsMutual: LiveData<Boolean>
        get() = _likeIsMutual

    private val _currentUser = MutableLiveData<ProfileData>()
    val currentUser: LiveData<ProfileData>
        get() = _currentUser

    private var userFeed: Queue<ProfileData> = LinkedList()

    fun getCurrentUserId() = _currentUser.value?.userId

    private fun nextUser() {

        _currentUser.value = userFeed.poll()
    }

    fun likeUser(onFailure: (error: String) -> Unit) {
        getCurrentUserId()?.let { userId ->
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
            nextUser()
        }
    }

    fun dislikeUser(onFailure: (error: String) -> Unit) {
        getCurrentUserId()?.let { userId ->
            viewModelScope.launch {
                val response =
                    repository.dislikeUser(userId = userId)
                if (response is ResultWrapper.Error) {
                    response.error?.let(onFailure)
                }
            }
            nextUser()
        }
    }

    fun getUserFeed(onFailure: (error: String) -> Unit) {
        viewModelScope.launch {
            val response =
                repository.getUserFeed()
            when (response) {
                is ResultWrapper.Success -> response.value?.let { list ->
                    userFeed = LinkedList(list.map { it.toDomain() })
                    _currentUser.value = userFeed.poll()
                }
                is ResultWrapper.Error -> response.error?.let(onFailure)
            }
        }
    }

}