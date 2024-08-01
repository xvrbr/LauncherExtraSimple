package com.example.extrasimple

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListeAppViewModel(application: Application) : AndroidViewModel(application) {
    private val _listeDesApps = MutableLiveData<List<App>>()
    val listeDesApps : LiveData<List<App>> = _listeDesApps

    init {
        viewModelScope.launch {
            fetchLaunchableApps()
        }
    }

    private suspend fun fetchLaunchableApps() {
        _listeDesApps.value = getLaunchableApps()
    }

    private suspend fun resfreshLaunchableApps():
            List<ApplicationInfo> = withContext(Dispatchers.IO) {
                val pm = getApplication<Application>().packageManager
                val listeApps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
                listeApps.filter { pm.getLaunchIntentForPackage(it.packageName) != null }
                    .sortedBy { it.loadLabel(pm).toString() }
            }

    private suspend fun getLaunchableApps():
            List<App> = withContext(Dispatchers.IO) {

        //Obtenir la liste des applications enregistrees
        val db = AppsBD(getApplication()).readableDatabase
        val curseur = db.query(
            "apps_settings",
            arrayOf("id_app", "nom_app", "package_name"),
            null,
            null,
            null,
            null,
            "nom_app"
        )
        var listeApps: MutableList<App> = mutableListOf()

        while (curseur.moveToNext()) {
            listeApps.add(
                App(
                    curseur.getInt(0),    //id
                    curseur.getString(1), //nomApp
                    curseur.getString(2)  //nomPackage
                )
            )
        }
        curseur.close()

        return@withContext listeApps
    }

}