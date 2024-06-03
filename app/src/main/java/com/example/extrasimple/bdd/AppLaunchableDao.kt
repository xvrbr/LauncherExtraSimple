package com.example.extrasimple.bdd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.extrasimple.bdd.AppLaunchable
import kotlinx.coroutines.flow.Flow

@Dao
interface AppLaunchableDao {

    @Query("SELECT * FROM APPS_LAUNCHABLES")
    fun getAllAppLaunchables(): List<AppLaunchable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAppLaunchable(appLaunchable: AppLaunchable)

    @Update
    fun updateAppLaunchable(appLaunchable: AppLaunchable)

    @Delete
    fun deleteAppLaunchable(appLaunchable: AppLaunchable)
}