package com.example.extrasimple

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppsBD(contexte: Context) :
    SQLiteOpenHelper(contexte, "AppsBD", null, VERSION_BD) {
        companion object {
        const val VERSION_BD = 4
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val requeteCreerTableAppsAccueil =
            "CREATE TABLE apps_accueil(" +
                    "id_app INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nom_app VARCHAR(100) NOT NULL," +
                    "package_name VARCHAR(100) NOT NULL);".trimIndent()
        val requeteCreerTableAppsSettings =
            "CREATE TABLE apps_settings(" +
                    "id_app INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nom_app VARCHAR(100) NOT NULL," +
                    "package_name VARCHAR(100) NOT NULL);".trimIndent()

        db?.execSQL(requeteCreerTableAppsAccueil)
        db?.execSQL(requeteCreerTableAppsSettings)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades here
        if (oldVersion < newVersion) {
            // Drop older table if needed and recreate
            db?.execSQL("DROP TABLE IF EXISTS apps_accueil")
            db?.execSQL("DROP TABLE IF EXISTS apps_settings")
            onCreate(db)
        }
    }
}