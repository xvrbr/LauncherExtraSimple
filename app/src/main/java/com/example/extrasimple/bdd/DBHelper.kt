package com.example.extrasimple.bdd

import android.content.Context
import androidx.room.Room

object DBHelper {

    private var db: RoomDB? = null

    @Synchronized
    fun getDatabase(context: Context): RoomDB {
        if (db == null) {
            try {
                db = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "launcher"
                ).build()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return db ?: throw IllegalStateException("Database is not initialized")
    }
}