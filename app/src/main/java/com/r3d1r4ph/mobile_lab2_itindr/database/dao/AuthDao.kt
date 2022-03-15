package com.r3d1r4ph.mobile_lab2_itindr.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.auth.AuthEntity

@Dao
interface AuthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuth(authEntity: AuthEntity)

    @Query("UPDATE auth SET profile = :profile")
    suspend fun updateProfile(profile: String)

    @Query("SELECT accessToken FROM auth")
    suspend fun getAccessToken(): List<String>

    @Query("SELECT refreshToken FROM auth")
    suspend fun getRefreshToken(): List<String>

    @Query("SELECT profile FROM auth LIMIT 1")
    fun getProfile(): LiveData<String?>

    @Query("SELECT * FROM auth")
    fun getAuthParams(): LiveData<List<AuthEntity>>

    @Query("DELETE FROM auth")
    suspend fun deleteAll()


}