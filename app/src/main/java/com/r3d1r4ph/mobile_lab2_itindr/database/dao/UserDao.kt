package com.r3d1r4ph.mobile_lab2_itindr.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.user.UserEntity
import com.r3d1r4ph.mobile_lab2_itindr.domain.ProfileData

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userEntities: List<UserEntity>)

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)

    @Transaction
    suspend fun updateUser(userId: String, data: ProfileData) {
        updateUserName(userId, data.name)
        data.aboutMyself?.let { updateUserAboutMyself(userId, it) }
        data.avatar?.let { updateUserAvatar(userId, it) }
    }

    @Query("UPDATE user SET name = :name WHERE userId = :userId")
    suspend fun updateUserName(userId: String, name: String)

    @Query("UPDATE user SET aboutMyself = :aboutMyself WHERE userId = :userId")
    suspend fun updateUserAboutMyself(userId: String, aboutMyself: String)

    @Query("UPDATE user SET avatar = :avatar WHERE userId = :userId")
    suspend fun updateUserAvatar(userId: String, avatar: String)

    @Query("SELECT * FROM user")
    fun observeAll(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getUserById(userId: String): UserEntity

    @Query("SELECT * FROM user")
    fun pagingSource(): PagingSource<Int, UserEntity>

    @Query("DELETE FROM user")
    suspend fun clearAll()
}