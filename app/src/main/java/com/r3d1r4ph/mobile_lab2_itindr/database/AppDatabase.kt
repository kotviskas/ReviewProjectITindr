package com.r3d1r4ph.mobile_lab2_itindr.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.r3d1r4ph.mobile_lab2_itindr.database.dao.*
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.auth.AuthEntity
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.chat.ChatEntity
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.message.MessageEntity
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.remotekey.RemoteKeyEntity
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.topic.TopicEntity
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.user.UserEntity

@Database(
    entities = [AuthEntity::class, UserEntity::class, RemoteKeyEntity::class, TopicEntity::class, ChatEntity::class, MessageEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getRemoteKeyDao(): RemoteKeyDao
    abstract fun getChatDao(): ChatDao
    abstract fun getAuthDao(): AuthDao
    abstract fun getTopicDao(): TopicDao
}