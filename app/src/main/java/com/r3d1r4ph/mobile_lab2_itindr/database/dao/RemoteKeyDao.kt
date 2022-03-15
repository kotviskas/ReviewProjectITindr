package com.r3d1r4ph.mobile_lab2_itindr.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.remotekey.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_key WHERE userId = :userId")
    suspend fun remoteKeyById(userId: String): RemoteKeyEntity?

    @Query("DELETE FROM remote_key")
    suspend fun clearAll()
}