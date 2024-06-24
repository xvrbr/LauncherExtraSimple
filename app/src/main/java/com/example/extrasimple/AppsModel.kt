package com.example.extrasimple

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppsModel: ViewModel() {
    private val listeApplications = MutableLiveData<List<App>>(emptyList())
    val listeApps: LiveData<List<App>> = listeApplications

    fun updateApps(nouvelleListe: List<App>){
        listeApplications.value = nouvelleListe
    }
}