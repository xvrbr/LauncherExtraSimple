package com.example.extrasimple

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.extrasimple.ui.theme.ExtraSimpleTheme


class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Obtenir la liste des applications enregistrees
        val db = AppsBD(this).readableDatabase
        val curseur = db.query("apps", arrayOf("id_app", "nom_app", "package_name"), null, null, null, null, null)
        var listeApps: MutableList<App> = mutableListOf()

        while(curseur.moveToNext()){
            listeApps.add(App(
                curseur.getInt(0), //id
                curseur.getString(1), //nomApp
                curseur.getString(2)) //nomPackage
            )
        }
        curseur.close()

        val modele: AppsModel by viewModels()
        modele.updateApps(listeApps)

        setContent {
            ExtraSimpleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    Row {
                        Column(
                            Modifier
                                .padding(start = 15.dp)
                                .padding(top = 30.dp)
                        ) {
                            //Ajoute les boutons des apps
                            ListeApps()
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {


                        FloatingActionButton(modifier = Modifier
                            .size(size = 56.dp)
                            .padding(all = 16.dp), onClick = {
                                val intentSettings = Intent(this@MainActivity, Settings::class.java)
                                startActivity(this@MainActivity, intentSettings, null)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                tint = Color.White,
                                contentDescription = "Settings"
                            )
                        }

                    }

                }
            }
        }
    }

    override fun onResume(){
        super.onResume()

        refreshAppList()
    }
    private fun refreshAppList() {
        val db = AppsBD(this).readableDatabase
        val cursor = db.query("apps", arrayOf("id_app", "nom_app", "package_name"), null, null, null, null, null)
        val updatedList: MutableList<App> = mutableListOf()

        while (cursor.moveToNext()) {
            updatedList.add(
                App(
                    cursor.getInt(0), //id
                    cursor.getString(1), //nomApp
                    cursor.getString(2) //nomPackage
                )
            )
        }
        cursor.close()
        val viewModel: AppsModel by viewModels()
        viewModel.updateApps(updatedList)
    }
    @Composable
    fun BtnTextApp(nomApp: String, nomPackage: String, pm: PackageManager) {
        Text(
            text = nomApp.lowercase(), fontSize = 20.sp, color = Color.White, letterSpacing = 3.sp, modifier = Modifier
                .height(40.dp)
                .clickable(
                    onClick = {
                        //Ouvre l'application selectionnee
                        val launchIntent = pm.getLaunchIntentForPackage(nomPackage)
                        startActivity(launchIntent)
                    }
                )
        )
    }
    @Composable
    fun ListeApps(){
        val pm = packageManager
        val modele: AppsModel by viewModels()
        val listeApps by modele.listeApps.observeAsState(emptyList())

        LazyColumn {
            items(items = listeApps, key={ application -> application.id}) { application ->
                BtnTextApp(
                    nomApp = application.nomApp,
                    nomPackage = application.nomPackage,
                    pm = pm
                )
            }
        }
    }

}
