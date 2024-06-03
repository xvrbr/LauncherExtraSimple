package com.example.extrasimple.bdd

import android.content.Context
import androidx.room.Room

object DBHelper {
    private var db: RoomDB? = null

    fun getDatabase(context: Context): RoomDB {
        if (db == null) {
            db = Room.databaseBuilder(
                context.applicationContext,
                RoomDB::class.java,
                "Launcher_BDD"
            ).build()
        }
        return db!!
    }
}