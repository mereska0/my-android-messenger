package com.example.messenger.data.repository

import com.example.messenger.data.local.AppDatabase

object Graph {
    lateinit var database: AppDatabase
        private set

    fun init(context: android.content.Context) {
        database = androidx.room.Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "messenger.db"
        ).build()
    }
}