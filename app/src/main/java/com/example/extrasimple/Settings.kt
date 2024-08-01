package com.example.extrasimple

import android.content.ContentValues
<<<<<<< Updated upstream
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
=======
>>>>>>> Stashed changes
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.extrasimple.ui.theme.ExtraSimpleTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import java.time.Instant
import java.util.Date
import java.util.Locale

class Settings : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

<<<<<<< Updated upstream
        //Get les apps installees
/////////////////////////////        //FAIRE CECI SUR UN AUTRE THREAD
        val pm = packageManager
        val listeApps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        val launchableApps = listeApps.filter { pm.getLaunchIntentForPackage(it.packageName) != null }

=======
>>>>>>> Stashed changes
        setContent{
            ExtraSimpleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ){
                    Row {
                        Column {
                            Spacer(modifier = Modifier.padding(top = 50.dp))

                            LazyColumn {
                                items(items = launchableApps) { application ->
                                    val packageInfo = pm.getPackageInfo(application.packageName, 0)
                                    ElementCheckList(
                                        nomApp = packageInfo.applicationInfo.loadLabel(pm).toString(),
                                        nomPackage = application.packageName,
                                        pm = pm,
                                        contexte = this@Settings
                                    )
                                }
                            }

                        }
                    }
                    Column(horizontalAlignment = Alignment.End){
                        Spacer(modifier = Modifier.padding(top = 300.dp))

                        //bouton retour
                        FloatingActionButton(modifier = Modifier
                            .background(color = Color.Blue)
                            .height(100.dp)
                            .size(size = 56.dp),
                            onClick = {
                                //Update la liste des applications pour MainActivity
                                val db = AppsBD(this@Settings).readableDatabase
                                val curseur = db.query("apps", arrayOf("id_app", "nom_app", "package_name"), null, null, null, null, null)

                                var listeAppsUpdatee: MutableList<App> = mutableListOf()
                                while(curseur.moveToNext()){
                                    listeAppsUpdatee.add(App(
                                        curseur.getInt(0),
                                        curseur.getString(1),
                                        curseur.getString(2))
                                    )
                                }
                                curseur.close()
                                val modele: AppsModel by viewModels()
                                modele.updateApps(listeAppsUpdatee)

                                //On retourne a la MainActivity
                                finish()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }

                        //Bouton refresh
                        FloatingActionButton(modifier = Modifier
                            .size(size = 30.dp),
                            onClick = {
                                // TODO: Refresh la liste des apps dans la bd et que ce soit affiche
                            }) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ElementCheckList(nomApp: String, nomPackage: String) {
        Row{
            //Verifier si l'app est deja dans la liste
<<<<<<< Updated upstream
            var db = AppsBD(contexte).readableDatabase
=======
            val db = AppsBD(this@Settings).readableDatabase
>>>>>>> Stashed changes
            val selectApp = db.query("apps", arrayOf("nom_app", "package_name"),
                "nom_app = ? and package_name = ?",
                arrayOf(nomApp, nomPackage), null, null, null)

            var isChecked by remember { mutableStateOf(selectApp.count > 0) }

            Checkbox(
                checked = isChecked,
                modifier = Modifier.padding(bottom = 10.dp),
                onCheckedChange = { newCheckedChange ->
                    isChecked = newCheckedChange

<<<<<<< Updated upstream
                    //Rajouter ou enlever l'app de la liste
                    if(isChecked){
                        val dbInsert = AppsBD(contexte).writableDatabase
                        dbInsert.insert("apps", null, ContentValues().apply {
                            put("nom_app", nomApp)
                            put("package_name", nomPackage)
                        })
                    }else{
                        val dbDelete = AppsBD(contexte).writableDatabase
                        dbDelete.delete("apps", "package_name = ?", arrayOf(nomPackage))
=======
                        //Rajouter ou enlever l'app de la liste
                        if(isChecked){
                            val dbInsert = AppsBD(this@Settings).writableDatabase
                            dbInsert.insert("apps", null, ContentValues().apply {
                                put("nom_app", nomApp)
                                put("package_name", nomPackage)
                            })
                        }else{
                            val dbDelete = AppsBD(this@Settings).writableDatabase
                            dbDelete.delete("apps", "package_name = ?", arrayOf(nomPackage))
                        }
                        selectApp.close()
>>>>>>> Stashed changes
                    }
                    selectApp.close()
                }
            )

            Text(text = nomApp,
                color = Color.White,
                fontSize = 20.sp,
<<<<<<< Updated upstream
                modifier = Modifier.padding(top = 10.dp)
            )
=======
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clickable(onClick = {
                        if (nomApp !in appsMaudites || compteurMaudits >= 50) {
                            //Ouvre l'application selectionnee
                            val pm = this@Settings.packageManager
                            val launchIntent = pm.getLaunchIntentForPackage(nomPackage)
                            startActivity(launchIntent)
                            finish()
                        } else {
                            compteurMaudits++
                        }
                    })
            )

        }
    }

    @Composable
    fun ListeDesApps(appListViewModel: ListeAppViewModel){

        val launchableApps by appListViewModel.listeDesApps.observeAsState(emptyList())

        LazyColumn {
            items(items = launchableApps) { application ->
                ElementCheckList(
                    nomApp = application.nomApp,
                    nomPackage = application.nomPackage
                )
            }
>>>>>>> Stashed changes
        }
    }
}