package com.example.darnamob.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.media.MediaDrm.SecurityLevel
import com.example.darnamob.Database.data.Admin
import com.example.darnamob.Database.data.Membre
import com.example.darnamob.Database.data.Prestation
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

    //=====================================================================================//
    //SIGN IN // LOG IN = METHODS

    //to check if user exists or not
    //tested
    fun checkEmail(email :String): Boolean{

        val db =readableDatabase
        var query = "SELECT * FROM ${Table_Schemas.Membre.TABLE_NAME} WHERE ${Table_Schemas.Membre.COLUMN_EMAIL} = '$email'"
        var cursor = db.rawQuery(query, null)


        if (cursor.count==0){ //IF NOT IN THE MEMBER TABLE GO CHECK IN THE ADMIN TABLE
            query = "SELECT * FROM ${Table_Schemas.Admin.TABLE_NAME} WHERE ${Table_Schemas.Admin.COLUMN_EMAIL} = '$email'"
            cursor = db.rawQuery(query, null)
        }
        return cursor.count>0
    }


    //to find the user
    //tested
    fun checkEmailPassword(email : String, password :String): Boolean{
        val db = readableDatabase
        var query= "SELECT * FROM ${Table_Schemas.Membre.TABLE_NAME} WHERE ${Table_Schemas.Membre.COLUMN_EMAIL} = '$email'" +
                " AND ${Table_Schemas.Membre.COLUMN_PASSWORD} = '${password.toSHA256()}'"
        var cursor = db.rawQuery(query, null)

        if (cursor.count == 0){
            query = "SELECT * FROM ${Table_Schemas.Admin.TABLE_NAME} WHERE ${Table_Schemas.Admin.COLUMN_EMAIL} = '$email' AND " +
                    "${Table_Schemas.Admin.COLUMN_PASSWORD} = '${password.toSHA256()}'"
            cursor = db.rawQuery(query, null)
        }

        return cursor.count>0

    }

    //method to add an admin account to the database
    //tested
    fun insertAdmin(admin : Admin){

        val db = writableDatabase
        val values = ContentValues().apply {
            put(Table_Schemas.Membre.COLUMN_EMAIL, admin.email)
            put(Table_Schemas.Membre.COLUMN_PASSWORD, admin.password.toSHA256())
        }

        db.insert(Table_Schemas.Admin.TABLE_NAME, null, values)
        db.close()

    }

    //Method to add prestations to the database
    //tested
    fun insertPrestations(presta : Prestation){

        val db = writableDatabase
        val values = ContentValues().apply {
            put(Table_Schemas.Prestation.COLUMN_PRESTAT, presta.prestat)
            put(Table_Schemas.Prestation.COLUMN_DOMAINE, presta.Domain)
            put(Table_Schemas.Prestation.COLUMN_PRICE, presta.Price)
            put(Table_Schemas.Prestation.COLUMN_DURATION, presta.Duration)
            put(Table_Schemas.Prestation.COLUMN_MATERIALS, presta.Materials)
        }

        db.insert(Table_Schemas.Prestation.TABLE_NAME, null, values)
        db.close()

    }

    //to add a new member in the table member
    //not tested
    fun insertAMembre(membre : Membre, boolClient : Boolean){ //for boolclient, if we are in admin it turns to 0

        val db = writableDatabase
        val values = ContentValues().apply {
            put(Table_Schemas.Membre.COLUMN_TEL, membre.tel)
            put(Table_Schemas.Membre.COLUMN_EMAIL, membre.email)
            put(Table_Schemas.Membre.COLUMN_IMAGE, membre.image)
            put(Table_Schemas.Membre.COLUMN_USERNAME, membre.userName)
            put(Table_Schemas.Membre.COLUMN_ADDRESS, membre.address)
            put(Table_Schemas.Membre.COLUMN_PASSWORD, membre.password.toSHA256())
            put(Table_Schemas.Membre.COLUMN_BOOLCLIENT, boolClient)

        }

        db.insert(Table_Schemas.Membre.TABLE_NAME, null, values)
        db.close()

    }

    //tested
    fun getDomains(): List<String>{
        val domainList = mutableListOf<String>()
        val db = readableDatabase
        val query = "SELECT DISTINCT ${Table_Schemas.Prestation.COLUMN_DOMAINE} FROM ${Table_Schemas.Prestation.TABLE_NAME}"

        val cursor = db.rawQuery(query, null)


        while (cursor.moveToNext()){
            val domain = cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Prestation.COLUMN_DOMAINE))
            domainList.add(domain)
        }
        cursor.close()
        db.close()
        return domainList
    }

    //to return the prestation according to the domain (for the dropdowns)
    //tested
    fun getPrestationbyDomain(Domain : String) : List<Prestation>{
        val prestationList = mutableListOf<Prestation>()
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Prestation.TABLE_NAME} WHERE ${Table_Schemas.Prestation.COLUMN_DOMAINE} = '$Domain' "
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val prestation = Prestation(cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Prestation.COLUMN_PRESTAT))
            , Domain,  cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Prestation.COLUMN_PRICE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Prestation.COLUMN_DURATION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Prestation.COLUMN_MATERIALS)))
            prestationList.add(prestation)
        }
        cursor.close()
        db.close()
        return prestationList
    }

}


