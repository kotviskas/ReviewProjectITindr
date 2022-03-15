package com.r3d1r4ph.mobile_lab2_itindr.repository.auth

import android.app.Application
import androidx.lifecycle.map
import androidx.room.withTransaction
import com.r3d1r4ph.mobile_lab2_itindr.database.Database
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.auth.AuthEntity

class AuthDBRepository(app: Application) {
    private val appDatabase = Database.getInstance(app)
    private val authDao = appDatabase.getAuthDao()

    suspend fun resetTokens(authEntity: AuthEntity) {
        appDatabase.withTransaction {
            authDao.deleteAll()
            authDao.insertAuth(authEntity)
        }
    }

    suspend fun deleteAll() {
        authDao.deleteAll()
    }

    fun getProfile() = authDao.getProfile()

    fun getAuthParams() = authDao.getAuthParams()

    suspend fun getAccessToken() = authDao.getAccessToken().firstOrNull()

    suspend fun updateProfile(profile: String) {
        authDao.updateProfile(profile)
    }
}