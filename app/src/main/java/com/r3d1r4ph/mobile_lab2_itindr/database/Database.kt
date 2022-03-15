package com.r3d1r4ph.mobile_lab2_itindr.database

import android.content.Context
import androidx.room.Room

object Database {
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase = synchronized(this) {
        instance ?: build(context.applicationContext).also { instance = it }
    }

    private fun build(context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, "app_database")
        .build()
}