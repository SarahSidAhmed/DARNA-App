package com.example.darnamob.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.darnamob.Database.data.Admin
import com.example.darnamob.Database.data.Artisan
import com.example.darnamob.Database.data.Membre
import com.example.darnamob.Database.data.Notification
import com.example.darnamob.Database.data.Prestation
import com.example.darnamob.toSHA256


class DatabaseHelper(Context : Context) : SQLiteOpenHelper(Context, DATABASE_NAME, null, DATABASE_VERSION ) {

    //=========================================================================================//
    //START OF CONSTANTS//
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

    //END OF CONSTANTS//
    //=====================================================================================//

    //======================================================================================//
    //START OF BDD CREATION//
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

    //END OF BDD CREATION//
    //=====================================================================================//

    //=====================================================================================//
    // START OF SIGN IN // LOG IN = METHODS

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
        val bool = cursor.count>0
        cursor.close()
        db.close()
        return bool
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

        val bool = cursor.count>0

        cursor.close()
        return bool

    }
    //END OF SIGN IN LOG IN METHODS//
    //====================================================================================//
    //====================================================================================//
    //START OF INSERTION METHODS//

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
    fun insertMembre(membre : Membre, boolClient : Boolean){ //for boolclient, if we are in admin it turns to 0

        val db = writableDatabase
        val values = ContentValues().apply {
            put(Table_Schemas.Membre.COLUMN_TEL, membre.tel)
            put(Table_Schemas.Membre.COLUMN_EMAIL, membre.email)
            put(Table_Schemas.Membre.COLUMN_IMAGE, membre.image)
            put(Table_Schemas.Membre.COLUMN_USERNAME, membre.userName)
            put(Table_Schemas.Membre.COLUMN_ADDRESS, membre.address)
            put(Table_Schemas.Membre.COLUMN_PASSWORD, membre.password.toSHA256())
            put(Table_Schemas.Membre.COLUMN_BOOLCLIENT, boolClient) //depends if we are in the admin side it means we added an artisan
                                                                  // otherwise it means it's a simple client
        }

        db.insert(Table_Schemas.Membre.TABLE_NAME, null, values)
        db.close()

    }

    fun insertArtisan(artisan : Artisan){
        val db = writableDatabase
        val membre = artisan.membre
        insertMembre(membre, false)

        val id = getUserID(artisan.membre.email)

        val values = ContentValues().apply {
            put(Table_Schemas.Artisan.COLUMN_ID, id)
            put(Table_Schemas.Artisan.COLUMN_DOMAIN, artisan.domain)
            put(Table_Schemas.Artisan.COLUMN_PRESTATION, artisan.prestation)
            put(Table_Schemas.Artisan.COLUMN_RATING, 0.0)
            put(Table_Schemas.Artisan.COLUMN_WORKING_AREA, "")
        }

        db.insert(Table_Schemas.Artisan.TABLE_NAME, null, values)
        db.close()

    }

    //END OF INSERTION METHODS//
    //=======================================================================================//

    //======================================================================================//
    //START OF GETTERS OF MEMBER DETAILS//
    fun getUserID(email : String): Int{

        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Membre.TABLE_NAME}" +
                " WHERE ${Table_Schemas.Membre.COLUMN_EMAIL} = '$email'"

        val cursor = db.rawQuery(query, null)

        cursor.moveToFirst()
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_ID))

        cursor.close()
        return id
    }

    fun getMembreByID(id : Int): Membre{
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Membre.TABLE_NAME} WHERE ${Table_Schemas.Membre.COLUMN_ID} = $id"

        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val membre = Membre(id,
            cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_TEL)),
            cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_ADDRESS)),
            cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_EMAIL)),
            cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_PASSWORD)),
            cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_USERNAME)),
            cursor.getBlob(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_IMAGE)),
            cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_REPORTS)))

        cursor.close()
        db.close()

        return membre

    }
    fun getArtisanByID(id : Int): Artisan{
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Artisan.TABLE_NAME} WHERE ${Table_Schemas.Artisan.COLUMN_ID} = $id"

        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val membre = getMembreByID(id)

        //getting all the infos
        val artisan = Artisan(membre,
            cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Artisan.COLUMN_DOMAIN)),
            cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Artisan.COLUMN_PRESTATION)),
            cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Artisan.COLUMN_DISPONIBLE))==1,
            cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Artisan.COLUMN_DEPLACEMENT))==1,
            cursor.getFloat(cursor.getColumnIndexOrThrow(Table_Schemas.Artisan.COLUMN_RATING)),
            cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Artisan.COLUMN_WORKING_AREA)))

        cursor.close()
        db.close()

        return artisan

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


    fun getAllUsers(): List<Artisan>{
        val db = readableDatabase
        val allUsersList = mutableListOf<Artisan>()
        val query = "SELECT * FROM ${Table_Schemas.Membre.TABLE_NAME}"
        val cursor = db.rawQuery(query, null)


        while (cursor.moveToNext()){

            //skimming the ids
            var id = cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_ID))

            //if it's an artisan getting all the artisan infos
            if(cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_BOOLCLIENT)) ==0){
            allUsersList.add(getArtisanByID(id)) //adding to the list of users
            }else{
                //if it's a member just getting the client infos
               var artisan =  Artisan(getMembreByID(id), "", "", false, false, 0.0f, "")
               allUsersList.add(artisan) // adding to the list of users
            }
        }

        cursor.close()
        db.close()

        return allUsersList


    }
    //retourned tout les clients de base de donnees
    fun getAllClient(): List<Membre>{
        val membersList = mutableListOf<Membre>()
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Membre.TABLE_NAME} WHERE ${Table_Schemas.Membre.COLUMN_BOOLCLIENT} = 1"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val membre = Membre(cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_TEL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_ADDRESS)),
             cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_EMAIL)),
             cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_PASSWORD)),
             cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_USERNAME)),
             cursor.getBlob(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_IMAGE)),
            cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_REPORTS)))

            membersList.add(membre)
        }

        cursor.close()
        db.close()

        return membersList
    }


    //not finished
//    fun getAllArtisan(): List<Artisan>{
//        val artisansList = mutableListOf<Artisan>()
//        val db = readableDatabase
//        val query = "SELECT * FROM ${Table_Schemas.Membre.TABLE_NAME} WHERE ${Table_Schemas.Membre.COLUMN_BOOLCLIENT} = 0"
//        val cursor = db.rawQuery(query, null)
//
//        while (cursor.moveToNext()){
//            val membre = Membre(cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_ID)),
//                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_TEL)),
//                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_ADDRESS)),
//                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_EMAIL)),
//                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_PASSWORD)),
//                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_USERNAME)),
//                cursor.getBlob(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_IMAGE)))
//
//            val query2 = "SELECT * FROM ${Table_Schemas.Artisan.TABLE_NAME} WHERE "
//        }
//
//        cursor.close()
//        db.close()
//
//        return artisansList
//        //return membersList
//    }

    //END OF GETTERS OF MEMBER DETAILS//
    //====================================================================================/
    //=====================================================================================/
    //START OF NOTIFIATION SYSTEM//
    fun notificationByID(id_reciever: Int): List<Notification>{

        val notifList = mutableListOf<Notification>()
        val db = readableDatabase

        val query = "SELECT * FROM ${Table_Schemas.Notification.TABLE_NAME} WHERE" +
                " ${Table_Schemas.Notification.COLUMN_ID_RECEIVER} = $id_reciever"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            val notif = Notification(id_reciever,
            cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Notification.COLUMN_ID_SENDER)),
            cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Notification.COLUMN_NUM_DEMANDE)),
            cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Notification.COLUMN_TYPE)))

            notifList.add(notif)
        }
        cursor.close()
        db.close()

        return notifList

    }

    fun deleteNotif(notif: Notification){
        val db = writableDatabase

        val query = "DELETE FROM ${Table_Schemas.Notification.TABLE_NAME} WHERE " +
                "${Table_Schemas.Notification.COLUMN_ID_RECEIVER} = ${notif.id_receiver}" +
                "AND ${Table_Schemas.Notification.COLUMN_ID_SENDER} = ${notif.id_sender}" +
                "AND ${Table_Schemas.Notification.COLUMN_NUM_DEMANDE} = ${notif.num_demande}"

        //SQL INJECTION
       db.execSQL(query, null)
    }

    fun decline(notif: Notification){
        val db = writableDatabase
        deleteNotif(notif)

        db.close()
    }

    fun confirm(notif : Notification){
        val db = writableDatabase

        //updating the request to confirmed
        val query = "UPDATE ${Table_Schemas.Demandes.TABLE_NAME} SET ${Table_Schemas.Demandes.COLUMN_CONFIRMED} = 1" +
                " WHERE ${Table_Schemas.Demandes.COLUMN_NUM_DEMANDE} = ${notif.num_demande}"

        db.execSQL(query, null)

        //adding the new confirmed requests to the tasks and rendez-vous of the client-Artisan
        val values = ContentValues().apply {
            put(Table_Schemas.Tasks_Rendez.COLUMN_NUM_DEMANDE, notif.num_demande)
            put(Table_Schemas.Tasks_Rendez.COLUMN_ID_ARTISAN, notif.id_sender)
            put(Table_Schemas.Tasks_Rendez.COLUMN_COMPLETED, 0)
        }

        db.insert(Table_Schemas.Tasks_Rendez.TABLE_NAME, null, values)


        //notification d'acceptation pour l'artisan
        val notifSender = ContentValues().apply {
            put(Table_Schemas.Notification.COLUMN_ID_RECEIVER, notif.id_sender)
            put(Table_Schemas.Notification.COLUMN_ID_SENDER, notif.id_receiver)
            put(Table_Schemas.Notification.COLUMN_TYPE, 2)
        }

        db.insert(Table_Schemas.Notification.TABLE_NAME, null, notifSender)

        //deleting the notification on the client side
        deleteNotif(notif)

        db.close()
    }

    //END OF NOTIFICATION SYSTEM//
    //=====================================================================================
    //=====================================================================================//
    //START ADMIN METHODS//
    fun banishUser(id : Int){

        val db = writableDatabase
        val query = "DELETE FROM ${Table_Schemas.Membre.TABLE_NAME} WHERE ${Table_Schemas.Membre.COLUMN_ID} = $id"

        db.execSQL(query, null)

    }
}



