package com.example.extrasimple.bdd

import android.content.pm.ApplicationInfo
import androidx.room.Entity

@Entity(tableName = "APPS_LAUNCHABLES")
data class AppLaunchable (
    var infoApp: ApplicationInfo
)