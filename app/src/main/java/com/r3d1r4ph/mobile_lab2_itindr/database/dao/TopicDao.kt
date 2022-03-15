package com.r3d1r4ph.mobile_lab2_itindr.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.topic.TopicEntity

@Dao
interface TopicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(topics: List<TopicEntity>)

    @Query("SELECT * FROM topic")
    fun observeAll(): LiveData<List<TopicEntity>>

    @Query("DELETE FROM topic")
    suspend fun deleteAll()
}