package com.r3d1r4ph.mobile_lab2_itindr.repository.user

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.r3d1r4ph.mobile_lab2_itindr.database.Database
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.user.UserEntity
import com.r3d1r4ph.mobile_lab2_itindr.domain.ProfileData

class UserDBRepository(context: Context) {
    private val userDao = Database.getInstance(context).getUserDao()

    fun observeAllUsers(): LiveData<List<ProfileData>> =
        userDao.observeAll().map { list -> list.map { it.toDomain() } }

    private suspend fun addNewUser(user: UserEntity) {
        userDao.addUser(user)
    }

    suspend fun getUserById(userId: String) = userDao.getUserById(userId).toDomain()

    suspend fun addNewUsers(users: List<UserEntity>) {
        userDao.insertAll(users)
    }
}