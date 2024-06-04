package com.example.extrasimple

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.room.Room
import com.example.extrasimple.bdd.DBHelper
import com.example.extrasimple.bdd.RoomDB
import com.example.extrasimple.ui.theme.ExtraSimpleTheme
import java.time.Instant
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {

    //Constantes
    private val NOMBRE_DE_CLICKS_POUR_SETTINGS = 2

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Gestion de la base de données
        val db = DBHelper.getDatabase(context = this)

        val appLaunchableDao = db.appLaunchableDao()

        //UI pis toute pis toute
        setContent {
            ExtraSimpleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                )
                {

                    Row {
                        Column {
                            //Petite Horloge tranquille
                            val currentTime = Instant.now()
                            val formattedTime = SimpleDateFormat("hh:mm", Locale.getDefault())
                                .format(Date.from(currentTime))
                            Text(text = formattedTime, color = Color.White, fontSize = 30.sp)
                            /*
                            LazyColumn {
                                items(items = launchableApps) { application ->
                                    val packageInfo = pm.getPackageInfo(application.packageName, 0)
                                    BtnTextApp(
                                        nomApp = packageInfo.applicationInfo.loadLabel(pm).toString(),
                                        nomPackage = application.packageName,
                                        pm = pm,
                                        contexte = this@MainActivity
                                    )
                                }
                            }*/

                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        //Bouton des settings
                        var compteurDeClicks = 0
                        FloatingActionButton(modifier = Modifier
                            .background(color = Color.Blue)
                            .size(size = 56.dp)
                            .padding(all = 16.dp), onClick = {
                            if (compteurDeClicks >= NOMBRE_DE_CLICKS_POUR_SETTINGS) {
                                val intentSettings = Intent(this@MainActivity, Settings::class.java)
                                startActivity(this@MainActivity, intentSettings, null)
                            }
                            compteurDeClicks++
                        }) {

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

    @Composable
    fun BtnTextApp(nomApp: String, nomPackage: String, pm: PackageManager, contexte: Context) {
        Text(
            text = nomApp, color = Color.White, modifier = Modifier.clickable(
                onClick = {
                    val launchIntent = pm.getLaunchIntentForPackage(nomPackage)

                    if (launchIntent != null) {
                        startActivity(contexte, launchIntent, null)
                    }
                }
            )
        )

    }
}
