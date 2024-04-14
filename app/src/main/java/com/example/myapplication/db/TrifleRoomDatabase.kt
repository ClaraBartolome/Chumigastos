package com.example.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.common.DB_NAME
import com.example.myapplication.db.models.TrifleModel


// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [TrifleModel::class], version = 1, exportSchema = false)
public abstract class TrifleRoomDatabase : RoomDatabase() {

    abstract fun linkSaverDao(): TrifleDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TrifleRoomDatabase? = null

        fun getDatabase(context: Context): TrifleRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TrifleRoomDatabase::class.java,
                    DB_NAME
                ).createFromAsset(DB_NAME)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}