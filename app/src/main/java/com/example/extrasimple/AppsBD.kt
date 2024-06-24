package com.example.extrasimple

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppsBD(contexte: Context) :
    SQLiteOpenHelper(contexte, "AppsBD", null, VERSION_BD) {
        companion object {
        const val VERSION_BD = 3
    }



    override fun onCreate(db: SQLiteDatabase?) {
        val requeteCreerTable =
            "CREATE TABLE apps(" +
                    "id_app INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "nom_app TEXT NOT NULL," +
                    "package_name TEXT NOT NULL)".trimIndent()

        db?.execSQL(requeteCreerTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades here
        if (oldVersion < newVersion) {
            // Drop older table if needed and recreate
            db?.execSQL("DROP TABLE IF EXISTS apps")
            onCreate(db)
        }
    }
}