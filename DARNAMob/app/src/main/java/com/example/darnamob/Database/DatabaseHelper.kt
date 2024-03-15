package com.example.darnamob.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.darnamob.toSHA256


class DatabaseHelper(Context : Context) : SQLiteOpenHelper(Context, DATABASE_NAME, null, DATABASE_VERSION ) {

    companion object{
        private const val DATABASE_NAME = "DARNA.db"
        private const val DATABASE_VERSION = 1

        //ARRAY CONTAINING ALL THE CREATE QUERIES OF THE DB TABLES
        val TABLES_CREATE_QUERIES = arrayOf(
            Table_Schemas.Membre.CREATE_QUERY,
            Table_Schemas.Artisan.CREATE_QUERY,
            Table_Schemas.Admin.CREATE_QUERY,
            Table_Schemas.Demandes.CREATE_QUERY,
            Table_Schemas.Comments.CREATE_QUERY,
            Table_Schemas.Notification.CREATE_QUERY,
            Table_Schemas.Prestation.CREATE_QUERY,
            Table_Schemas.Tasks_Rendez.CREATE_QUERY,
            Table_Schemas.WorkDays.CREATE_QUERY)

        //ARRAY CONTAINING ALL THE TABLE NAMES OF THE DB TABLES
        val TABLE_NAMES = arrayOf(
            Table_Schemas.Membre.TABLE_NAME,
            Table_Schemas.Artisan.TABLE_NAME,
            Table_Schemas.Admin.TABLE_NAME,
            Table_Schemas.Demandes.TABLE_NAME,
            Table_Schemas.Comments.TABLE_NAME,
            Table_Schemas.Notification.TABLE_NAME,
            Table_Schemas.Prestation.TABLE_NAME,
            Table_Schemas.Tasks_Rendez.TABLE_NAME,
            Table_Schemas.WorkDays.TABLE_NAME)

    }
    override fun onCreate(db: SQLiteDatabase?) {
        for(query in TABLES_CREATE_QUERIES){
            db?.execSQL(query)
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        for(Table_name in TABLE_NAMES){
        val dropTableQuery = "DROP TABLE IF EXISTS $Table_name"
        db?.execSQL(dropTableQuery)
        }
        onCreate(db)
    }

    fun insertAdmin(admin : Admin){

        val db = writableDatabase
        val values = ContentValues().apply {
            put(Table_Schemas.Membre.COLUMN_EMAIL, admin.email)
            put(Table_Schemas.Membre.COLUMN_PASSWORD, admin.password.toSHA256())
        }

        db.insert(Table_Schemas.Admin.TABLE_NAME, null, values)
        db.close()

    }

}