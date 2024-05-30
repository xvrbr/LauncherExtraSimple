package com.example.extrasimple

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.extrasimple.ui.theme.ExtraSimpleTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExtraSimpleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    val listeApps: List<PackageInfo> = obtenirListeApps(this)
                    Column {
                        for (packageInfo in listeApps) {
                            val appName = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString()
                            Text(appName, color = Color.White)
                        }
                    }

                }
            }
        }
    }
}


@Composable
fun obtenirListeApps(contexte: Context): List<PackageInfo> {
    val pm : PackageManager = contexte.packageManager
    val packages: List<PackageInfo> = pm.getInstalledPackages(PackageManager.GET_META_DATA)
    return packages
}

