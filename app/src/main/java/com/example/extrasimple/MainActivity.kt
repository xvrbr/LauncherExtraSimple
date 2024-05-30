package com.example.extrasimple

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.extrasimple.ui.theme.ExtraSimpleTheme
import java.sql.Time
import java.time.Clock
import java.time.Instant
import java.util.Date
import java.util.Locale
class MainActivity : ComponentActivity() {

    //Constantes
    val NOMBRE_DE_CLICKS_POUR_SETTINGS = 50

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExtraSimpleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                )
                {
                    //Petite Horloge tranquille
                    val currentTime = Instant.now()
                    val formattedTime = SimpleDateFormat("hh:mm", Locale.getDefault())
                        .format(Date.from(currentTime))
                    Text(text = formattedTime, color = Color.White, fontSize = 30.sp)

                    //Boutons-texte de noms d'app
                    val pm: PackageManager = this.packageManager
                    val listeApps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
                    Column {
                        for (application in listeApps) {
                            btnTextApp(
                                application.loadLabel(pm).toString(),
                                application.packageName,
                                pm,
                                this@MainActivity
                            )
                        }
                    }

                    //Bouton des settings
                    var compteurDeClicks = 0
                    Button(onClick = {
                        if (compteurDeClicks == NOMBRE_DE_CLICKS_POUR_SETTINGS) {
                            val intentSettings = Intent(this, MainActivity::class.java)
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

    @Composable
    fun btnTextApp(nomApp: String, nomPackage: String, pm: PackageManager, contexte: Context) {
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
