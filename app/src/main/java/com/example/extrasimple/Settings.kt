package com.example.extrasimple

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.material3.Checkbox
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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.extrasimple.bdd.AppLaunchable
import com.example.extrasimple.bdd.AppLaunchableDao
import com.example.extrasimple.bdd.DBHelper
import com.example.extrasimple.ui.theme.ExtraSimpleTheme
import kotlinx.coroutines.flow.Flow

class Settings : ComponentActivity() {

    //Get les apps installees
    val pm = packageManager
    val listeApps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
    val launchableApps = listeApps.filter { pm.getLaunchIntentForPackage(it.packageName) != null }

    //Liste des apps a mettre dans la page de launcher
    val db = DBHelper.getDatabase(context = this)
    val appsALauncher = db.appLaunchableDao().getAllAppLaunchables()

    //Liste des noms des packageName des apps
    val listePackageName = appsALauncher.map {it.infoApp.packageName}


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




        //UI
        setContent{
            ExtraSimpleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ){
                    Row {
                        //Liste des apps
                        Column {
                            Spacer(modifier = Modifier.padding(top = 50.dp))

                            LazyColumn {
                                items(items = launchableApps) { application ->
                                    val packageInfo = pm.getPackageInfo(application.packageName, 0)
                                    ElementCheckList(
                                        app = packageInfo.applicationInfo
                                    )
                                }
                            }

                        }
                    }
                    //Bouton de retour
                    Column(horizontalAlignment = Alignment.End){
                        Spacer(modifier = Modifier.padding(top = 300.dp))

                        FloatingActionButton(modifier = Modifier
                            .background(color = Color.Blue)
                            .height(100.dp)
                            .size(size = 56.dp),
                            onClick = {
                            finish()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ElementCheckList(app: ApplicationInfo, checked : Boolean ?= null) {
        Row{
            Checkbox(
                checked = (app.packageName in listePackageName),
                modifier = Modifier.padding(bottom = 10.dp),
                onCheckedChange = { isChecked ->
                //Rajouter ou enlever l'app de la liste
                    if (isChecked) {
                        db.appLaunchableDao().insertAppLaunchable(AppLaunchable(app))
                    } else {
                        db.appLaunchableDao().deleteAppLaunchable(AppLaunchable(app))
                    }
            })

            Text(text = app.name, color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(top = 10.dp))
        }
    }

}