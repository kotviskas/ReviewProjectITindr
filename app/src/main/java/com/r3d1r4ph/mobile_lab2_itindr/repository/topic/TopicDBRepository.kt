package com.r3d1r4ph.mobile_lab2_itindr.repository.topic

import android.app.Application
import com.r3d1r4ph.mobile_lab2_itindr.database.Database
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.topic.TopicEntity

class TopicDBRepository(app: Application) {
    private val topicDao = Database.getInstance(app).getTopicDao()

    fun observeAll() = topicDao.observeAll()

    suspend fun deleteAll() {
        topicDao.deleteAll()
    }

    suspend fun insertAll(topics: List<TopicEntity>) {
        topicDao.insertAll(topics)
    }
}