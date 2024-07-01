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
    private val _listeDesApps = MutableLiveData<List<ApplicationInfo>>()
    val listeDesApps : LiveData<List<ApplicationInfo>> = _listeDesApps

    init {
        viewModelScope.launch {
            fetchLaunchableApps()
        }
    }

    private suspend fun fetchLaunchableApps() {
        _listeDesApps.value = getLaunchableApps()
    }

    private suspend fun getLaunchableApps():
            List<ApplicationInfo> = withContext(Dispatchers.IO) {
                val pm = getApplication<Application>().packageManager
                val listeApps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
                listeApps.filter { pm.getLaunchIntentForPackage(it.packageName) != null }
                    .sortedBy { it.loadLabel(pm).toString() }
            }

}