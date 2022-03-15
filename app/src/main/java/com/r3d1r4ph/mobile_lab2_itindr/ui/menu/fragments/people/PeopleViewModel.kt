package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.people

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.r3d1r4ph.mobile_lab2_itindr.domain.ProfileData
import kotlinx.coroutines.flow.Flow

class PeopleViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = PeopleRepository(app)

    @ExperimentalPagingApi
    fun fetchUsers(): Flow<PagingData<ProfileData>> =
        repository.letUsersFlow().cachedIn(viewModelScope)

}