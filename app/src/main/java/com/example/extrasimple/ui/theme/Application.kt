package com.example.extrasimple.ui.theme

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

class Application {

    fun obtenirListeDesApps(contexte: Context) : List<ApplicationInfo>{

        val listeApps = contexte.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        return listeApps
    }
}