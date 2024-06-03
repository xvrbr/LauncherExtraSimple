package com.example.extrasimple.bdd

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AppLaunchable::class], version = 1)
abstract class RoomDB : RoomDatabase() {
    abstract fun appLaunchableDao() : AppLaunchableDao

}