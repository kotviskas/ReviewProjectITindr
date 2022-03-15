package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.people

import android.app.Application
import androidx.paging.*
import com.r3d1r4ph.mobile_lab2_itindr.database.Database
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.user.UserService
import com.r3d1r4ph.mobile_lab2_itindr.domain.ProfileData
import com.r3d1r4ph.mobile_lab2_itindr.utils.UserRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PeopleRepository(app: Application) {

    private val appDatabase = Database.getInstance(app)
    private val userService = UserService()

    @ExperimentalPagingApi
    fun letUsersFlow(): Flow<PagingData<ProfileData>> {
        return Pager(
            config = PagingConfig(
                pageSize = UserRemoteMediator.PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = UserRemoteMediator(
                database = appDatabase,
                networkService = userService
            ),
            pagingSourceFactory = { appDatabase.getUserDao().pagingSource() }
        ).flow
            .map { list ->
                list.map { it.toDomain() }
            }
    }
}