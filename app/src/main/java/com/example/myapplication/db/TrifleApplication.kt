package com.example.myapplication.db

import android.app.Application

class TrifleApplication: Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { TrifleRoomDatabase.getDatabase(this) }
    val repository by lazy { TrifleRepository(database.linkSaverDao()) }
}