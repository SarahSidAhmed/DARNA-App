package com.example.darnamob.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.darnamob.Database.data.Admin
import com.example.darnamob.Database.data.Artisan
import com.example.darnamob.Database.data.Comment
import com.example.darnamob.Database.data.Demande
import com.example.darnamob.Database.data.Membre
import com.example.darnamob.Database.data.Notification
import com.example.darnamob.Database.data.Prestation
import com.example.darnamob.Database.data.RendezVousTasks
import com.example.darnamob.toSHA256


class DatabaseHelper(Context: Context) : SQLiteOpenHelper(Context, DATABASE_NAME, null, DATABASE_VERSION ) {

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

        val data = PrepopulatedData()
        //insert prepopulated DATA

        if(!prestationisEmpty(db)){
            var i = 0
            while(i<23) {
                val values = ContentValues().apply {
                    put(Table_Schemas.Prestation.COLUMN_PRESTAT, data.prestat[i])
                    put(Table_Schemas.Prestation.COLUMN_DOMAINE, data.domaine[i])
                    put(Table_Schemas.Prestation.COLUMN_DURATION, data.duration[i])
                    put(Table_Schemas.Prestation.COLUMN_PRICE, data.price[i])
                    put(Table_Schemas.Prestation.COLUMN_MATERIALS, data.materials[i])
                }
                db?.insert(Table_Schemas.Prestation.TABLE_NAME, null, values)

                i++
            }
        }

        if (!adminisEmpty(db)) {
            var j = 0
            while (j < 2) {
                val values = ContentValues().apply {
                    put(Table_Schemas.Admin.COLUMN_EMAIL, data.emailAdmins[j])
                    put(Table_Schemas.Admin.COLUMN_PASSWORD, data.password[j].toSHA256())
                }
                db?.insert(Table_Schemas.Admin.TABLE_NAME, null, values)
                j++
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        for(Table_name in TABLE_NAMES){
        val dropTableQuery = "DROP TABLE IF EXISTS $Table_name"
        db?.execSQL(dropTableQuery)
        }
        onCreate(db)
    }


    //PREPOPULATED DATABASE DATA

    private fun prestationisEmpty(db: SQLiteDatabase?): Boolean{
        val query = "SELECT COUNT(*) FROM ${Table_Schemas.Prestation.TABLE_NAME}"
        val cursor = db?.rawQuery(query, null)

        val count = cursor?.count==0
        cursor?.close()
        return count
    }


    private fun adminisEmpty(db: SQLiteDatabase?): Boolean{
        val query = "SELECT COUNT(*) FROM ${Table_Schemas.Admin.TABLE_NAME}"
        val cursor = db?.rawQuery(query, null)

        val count = cursor?.count==0
        cursor?.close()
        return count
    }

    // RESUMER OF ALL THE METHODS//
    //===========================================================================================
    //SIGN IN SIGN UP
    // -checkEmail(email:String) -> Boolean
    // -checkEmailPassword(email : String, password :String) -> Boolean
    // -checkIfClient(email: String) -> Boolean
    // -checkIfAdmin(email: String) ->Boolean

    //INSERTING USERS AND DATA
    // -insertAdmin(admin : Admin)
    // -insertPrestations(presta : Prestation)
    // -insertMembre(membre : Membre, boolClient : Boolean)
    // -insertArtisan(artisan : Artisan)  equivalent to create new account for an artisan


    //EDITING PROFILE METHODS
    // -editProfileMember(id: Int, phoneNumber: String, Address: String)
    //editPorfileArtisan(idArtisan: Int, workArea: String, workHours: String, deplacement: Boolean, disponible: Boolean, workdays: List<Int>)
    // -editPassword(idMembre: Int, confirmationPassword: String, password: String): Boolean

    //SEARCHING USERS BY ID & EMAIL
    // -getUserID(email : String) -> Int
    // -getMembreByID(id : Int) -> Membre
    // -getArtisanByID(id : Int) -> Artisan
    // -getMemberEmailByID(id: Int) -> String
    // -getMemberPhoneByID(id:Int) -> String

    //DOMAINS AND PRESTATIONS
    // -getDomains() ->  List<String>
    // -getPrestationbyDomain(Domain : String) -> List<Prestation>
    // -getPrestationPrice(prestation: String): Int

    //RETRIEVING USERS / CLIENTS
    // -getAllUsers() -> List<Artisan>
    // -getAllClient() -> List<Membre>

    //NOTIFICATIONS
    // -notificationByID(id_reciever: Int) -> List<Notification>
    // -deleteNotif(notif: Notification)
    // -decline(notif: Notification)
    // -confirm(notif : Notification)
    // -insertNotifRating(task: RendezVousTasks)
    // -insertNotifServiceRequest(idClient: Int, idArtisan: Int, num_demande: Int)
    // -insertADminWarning(idUser: Int)


    //ADMIN BANISHING
    // -banishUser(id : Int)

    //SEARCHING METHODS
    // -searchUserByName(username: String) -> List<Artisan>
    // -reportedUsers() -> List<Artisan>
    // -searchDemandeByAddress(address: String) -> List<Demande>
    //

    //DEMANDE
    // -getAllDemandeByRegionDispo(region: String, dispo: Boolean) -> List<Demande> (artisan)
    // -getTasksArtisan(artisanId: Int) -> List<RendezVousTasks>
    // -getRendezVousClient(clientId: Int) -> List<RendezVousTasks> (client)
    // -addDemande(demande: Demande)
    // -filterRendezVousByCategorie(clientId: Int,categorie: String): List<Demande>
    // -getDemande(num_demande: Int) : Demande


    //COMMENT & RATING
    // -commenterExist(commenterId: Int, artisanId: Int) -> Boolean (you don't need it in the integration)
    // -addComment(artisanId: Int, commenterId: Int, commentText: String)
    // -addRating(artisanId: Int, commenterId: Int, notation: Float)
    // -getAllArtisanComments(artisanId: Int) -> List<Comment>



    //=======================================================================================

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

    fun checkIfClient(email: String):Boolean{
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Membre.TABLE_NAME} WHERE ${Table_Schemas.Membre.COLUMN_EMAIL} = '$email'"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val booClient = cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_BOOLCLIENT))==1
        cursor.close()
        return booClient
    }

    fun checkIfAdmin(email: String):Boolean{
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Admin.TABLE_NAME} WHERE ${Table_Schemas.Admin.COLUMN_EMAIL} = '$email'"
        val cursor = db.rawQuery(query, null)
        val isAdmin = cursor.count>0
        cursor.close()
        return isAdmin
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

        val membre = artisan.membre
        insertMembre(membre, false)

        val id = getUserID(artisan.membre.email)

        val db = writableDatabase
        val values = ContentValues().apply {
            put(Table_Schemas.Artisan.COLUMN_ID, id)
            put(Table_Schemas.Artisan.COLUMN_DOMAIN, artisan.domain)
            put(Table_Schemas.Artisan.COLUMN_WORK_HOURS, "")
            put(Table_Schemas.Artisan.COLUMN_DOMAIN, artisan.domain)
            put(Table_Schemas.Artisan.COLUMN_PRESTATION, artisan.prestation)
            put(Table_Schemas.Artisan.COLUMN_RATING, 0.0)
            put(Table_Schemas.Artisan.COLUMN_WORKING_AREA, artisan.work_Area)
        }

        db.insert(Table_Schemas.Artisan.TABLE_NAME, null, values)

        val valuesWorkdays = ContentValues().apply {
            put(Table_Schemas.WorkDays.COLUMN_ID, id)
            put(Table_Schemas.WorkDays.COLUMN_SATURDAY, 0)
            put(Table_Schemas.WorkDays.COLUMN_SUNDAY, 0)
            put(Table_Schemas.WorkDays.COLUMN_MONDAY, 0)
            put(Table_Schemas.WorkDays.COLUMN_TUESDAY, 0)
            put(Table_Schemas.WorkDays.COLUMN_WEDNESDAY, 0)
            put(Table_Schemas.WorkDays.COLUMN_THURSDAY, 0)
            put(Table_Schemas.WorkDays.COLUMN_FRIDAY, 0)
        }
        db.insert(Table_Schemas.WorkDays.TABLE_NAME, null, valuesWorkdays)

        db.close()

    }

    //END OF INSERTION METHODS//
    //=======================================================================================//

    //EDITTING PROFILE METHODS//
    //=======================================================================================//

    //METHOD TO EDIT THE PROFILE OF A SIMPLE MEMBER
    fun editProfileMember(id: Int, phoneNumber: String, Address: String, image: ByteArray){
        val db = writableDatabase
        val query = "UPDATE ${Table_Schemas.Membre.TABLE_NAME} " +
                "SET ${Table_Schemas.Membre.COLUMN_TEL} = ?, " +
                "${Table_Schemas.Membre.COLUMN_ADDRESS} = ?, " +
                "${Table_Schemas.Membre.COLUMN_IMAGE} = ? " +
                "WHERE ${Table_Schemas.Membre.COLUMN_ID} = ?"


        db.execSQL(query, arrayOf(phoneNumber, Address, image, id))
        db.close()

    }

    //METHOD TO EDIT PROFILE ARTISAN (ENABLE TO EDIT THEIR EMAIL AND PHONE NUMBER
    fun editPorfileArtisan(idArtisan: Int, workArea: String, workHours: String, deplacement: Boolean,
                     disponible: Boolean, workdays: List<Int>, image : ByteArray){
        val db = writableDatabase
        val query = "UPDATE ${Table_Schemas.Artisan.TABLE_NAME} " +
                "SET ${Table_Schemas.Artisan.COLUMN_WORKING_AREA} = ?," +
                "${Table_Schemas.Artisan.COLUMN_WORK_HOURS} = ?," +
                "${Table_Schemas.Artisan.COLUMN_DEPLACEMENT} = ?," +
                "${Table_Schemas.Artisan.COLUMN_DISPONIBLE} = ? " +
                "WHERE ${Table_Schemas.Artisan.COLUMN_ID} = ?"

        //editting the workdays
        val query2 = "UPDATE ${Table_Schemas.WorkDays.TABLE_NAME} " +
                "SET ${Table_Schemas.WorkDays.COLUMN_SATURDAY} = ?, "+
                "${Table_Schemas.WorkDays.COLUMN_SUNDAY} = ?, "+
                "${Table_Schemas.WorkDays.COLUMN_MONDAY} = ?, "+
                "${Table_Schemas.WorkDays.COLUMN_TUESDAY} = ?, "+
                "${Table_Schemas.WorkDays.COLUMN_WEDNESDAY} = ?, "+
                "${Table_Schemas.WorkDays.COLUMN_THURSDAY} = ?, "+
                "${Table_Schemas.WorkDays.COLUMN_FRIDAY} = ? " +
                "WHERE ${Table_Schemas.WorkDays.COLUMN_ID} = $idArtisan"


        val query3 = "UPDATE ${Table_Schemas.Membre.TABLE_NAME}" +
                " SET ${Table_Schemas.Membre.COLUMN_IMAGE} = ?" +
                "WHERE ${Table_Schemas.Membre.COLUMN_ID} = ?"



        db.execSQL(query, arrayOf(workArea, workHours, deplacement, disponible, idArtisan))
        db.execSQL(query2,
            arrayOf(workdays[0],
                    workdays[1],
                    workdays[2],
                    workdays[3],
                    workdays[4],
                    workdays[5],
                    workdays[6]))
        db.execSQL(query3, arrayOf(image, idArtisan))
        db.close()
    }



    //METHOD TO EDIT PASSWORD AFTER CONFIRMATION OF THE OLD PASSWORD
    fun editPassword(idMembre: Int, confirmationPassword: String, password: String): Boolean{
        var db = readableDatabase
        val coded = password.toSHA256()
        //${password.toSHA256()}'
//        val query = "UPDATE ${Table_Schemas.Membre.TABLE_NAME} " +
//                "SET ${Table_Schemas.Membre.COLUMN_PASSWORD} = '$coded' " +
//                "WHERE ${Table_Schemas.Membre.COLUMN_ID} = $idMembre"

        val q = "UPDATE ${Table_Schemas.Membre.TABLE_NAME} " +
                "SET ${Table_Schemas.Membre.COLUMN_PASSWORD} = ? " +
                "WHERE ${Table_Schemas.Membre.COLUMN_ID} = ?"

        val passwordCheckquery = "SELECT * FROM ${Table_Schemas.Membre.TABLE_NAME} " +
                "WHERE ${Table_Schemas.Membre.COLUMN_ID} = $idMembre"

        val cursor = db.rawQuery(passwordCheckquery, null)

        cursor.moveToFirst()
        val bool = cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_PASSWORD)) == confirmationPassword.toSHA256()
        if (bool){
            db = writableDatabase
        db.execSQL(q, arrayOf(coded, idMembre))
        }

        cursor.close()
        db.close()

        return bool
    }


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
        db.close()
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
        //db.close()

        return membre

    }


    fun getArtisanByID(id : Int): Artisan{
        var db = readableDatabase
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
//        db.close()

        return artisan

    }

    //returns the email of the user
    fun getMemberEmailByID(id: Int): String{
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Membre.TABLE_NAME} WHERE ${Table_Schemas.Membre.COLUMN_ID} = $id"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val email = cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_EMAIL))

        cursor.close()
        db.close()

        return email
    }

    //returns the phone of the user with the id
    fun getMemberPhoneByID(id:Int): String{
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Membre.TABLE_NAME} " +
                "WHERE ${Table_Schemas.Membre.COLUMN_ID} = $id"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val phone = cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_TEL))

        cursor.close()
        db.close()

        return phone
    }

    fun getWorkHour(id: Int): String{
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Artisan.TABLE_NAME}" +
                " WHERE ${Table_Schemas.Artisan.COLUMN_ID} = $id"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val workHours = cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Artisan.COLUMN_WORK_HOURS))

        cursor.close()
        db.close()
        return workHours
    }

    fun getWorkDays(id: Int): Array<Boolean>{
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.WorkDays.TABLE_NAME}" +
                " WHERE ${Table_Schemas.WorkDays.COLUMN_ID} = $id"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val days = arrayOf(
            cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.WorkDays.COLUMN_SATURDAY))==1,
                    cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.WorkDays.COLUMN_SUNDAY))==1,
                    cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.WorkDays.COLUMN_MONDAY))==1,
                    cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.WorkDays.COLUMN_TUESDAY))==1,
                    cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.WorkDays.COLUMN_WEDNESDAY))==1,
                    cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.WorkDays.COLUMN_THURSDAY))==1,
                    cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.WorkDays.COLUMN_FRIDAY))==1
        )


        cursor.close()
        db.close()
        return days
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


    //returns the price of the selected prestation
    fun getPrestationPrice(prestation: String): Int{
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Prestation.TABLE_NAME}" +
                " WHERE ${Table_Schemas.Prestation.COLUMN_PRESTAT} = '$prestation'"

        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val price = cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Prestation.COLUMN_PRICE))

        cursor.close()
        db.close()

        return price
    }

    fun getPrestationDuration(prestat: String): Int{
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Prestation.TABLE_NAME} WHERE ${Table_Schemas.Prestation.COLUMN_PRESTAT} = '$prestat"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val duration = cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Prestation.COLUMN_DURATION))

        cursor.close()
        db.close()
        return duration
    }

    //END DOMAINS & PRESTATIONS
    //==================================================================================================
    //START RETRIEVING USERS / CLIENTS

    fun getAllUsers(): List<Artisan>{
        var db = readableDatabase

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


    //RETRIEVING USERS / CLIENTS//
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
                "${Table_Schemas.Notification.COLUMN_ID_RECEIVER} = ?" +
                " AND ${Table_Schemas.Notification.COLUMN_ID_SENDER} = ?" +
                " AND ${Table_Schemas.Notification.COLUMN_NUM_DEMANDE} = ?"

        //SQL INJECTION
       db.execSQL(query, arrayOf(notif.id_receiver, notif.id_sender, notif.num_demande))
    }

    fun decline(notif: Notification){
        val db = writableDatabase
        deleteNotif(notif)
        db.close()
    }

    fun confirm(notif : Notification){
        val db = writableDatabase

        //updating the request to confirmed
        val query = "UPDATE ${Table_Schemas.Demandes.TABLE_NAME} SET ${Table_Schemas.Demandes.COLUMN_CONFIRMED} = ?" +
                 " WHERE ${Table_Schemas.Demandes.COLUMN_NUM_DEMANDE} = ?"

        db.execSQL(query, arrayOf( 1, notif.num_demande))

        //adding the new confirmed requests to the tasks and rendez-vous of the client-Artisan
        val values = ContentValues().apply {
            put(Table_Schemas.Tasks_Rendez.COLUMN_NUM_DEMANDE, notif.num_demande)
            put(Table_Schemas.Tasks_Rendez.COLUMN_ID_ARTISAN, notif.id_receiver)
            put(Table_Schemas.Tasks_Rendez.COLUMN_ID_CLIENT, notif.id_sender)
            put(Table_Schemas.Tasks_Rendez.COLUMN_COMPLETED, 0)
        }

        db.insert(Table_Schemas.Tasks_Rendez.TABLE_NAME, null, values)


        //notification d'acceptation pour l'artisan
        val notifSender = ContentValues().apply {
            put(Table_Schemas.Notification.COLUMN_ID_RECEIVER, notif.id_receiver)
            put(Table_Schemas.Notification.COLUMN_ID_SENDER, notif.id_sender)
            put(Table_Schemas.Notification.COLUMN_TYPE, 2)
        }

        db.insert(Table_Schemas.Notification.TABLE_NAME, null, notifSender)

        //deleting the notification on the client side


        db.close()
    }


    //notification to rate the artisan after the end of the task
    fun insertNotifRating(task: RendezVousTasks){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Table_Schemas.Notification.COLUMN_ID_RECEIVER, task.clientId)
            put(Table_Schemas.Notification.COLUMN_ID_SENDER, task.artisanId)
            put(Table_Schemas.Notification.COLUMN_NUM_DEMANDE, task.num_demande)
            put(Table_Schemas.Notification.COLUMN_TYPE, 3)
        }

        db.insert(Table_Schemas.Notification.TABLE_NAME, null, values)
        db.close()
    }

    //to insert the service suggestion of the artisan to the client
    fun insertNotifServiceRequest(idClient: Int, idArtisan: Int, num_demande: Int){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Table_Schemas.Notification.COLUMN_ID_RECEIVER, idClient)
            put(Table_Schemas.Notification.COLUMN_ID_SENDER, idArtisan)
            put(Table_Schemas.Notification.COLUMN_NUM_DEMANDE, num_demande)
            put(Table_Schemas.Notification.COLUMN_TYPE, 1)
        }

        db.insert(Table_Schemas.Notification.TABLE_NAME, null, values)
        db.close()
    }


    //to insert the admin warning
    fun insertADminWarning(idUser: Int){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Table_Schemas.Notification.COLUMN_ID_SENDER, -1) //-1 to indicate that it's from the admin
            put(Table_Schemas.Notification.COLUMN_ID_RECEIVER, idUser)
            put(Table_Schemas.Notification.COLUMN_NUM_DEMANDE, -1) //not of the type demande
            put(Table_Schemas.Notification.COLUMN_TYPE, 0)
        }

        db.insert(Table_Schemas.Notification.TABLE_NAME, null, values)
        db.close()
    }

    //END OF NOTIFICATION SYSTEM//
    //=====================================================================================
    //=====================================================================================//
    //START ADMIN METHODS//
    fun banishUser(id : Int){

        val db = writableDatabase
        val query = "DELETE FROM ${Table_Schemas.Membre.TABLE_NAME} WHERE ${Table_Schemas.Membre.COLUMN_ID} = $id"

        db.execSQL(query)

    }


    //END ADMIN METHODS//
    //=======================================================================================//
    //START SEARCHING METHODS//

    //SEARCH METHODS FOR ADMIN TO FIND USERS BY THEIR USERNAME
    fun searchUserByName(username: String): List<Artisan>{
        val db = readableDatabase
        val users = mutableListOf<Artisan>()
        val query = "SELECT * FROM ${Table_Schemas.Membre.TABLE_NAME} WHERE ${Table_Schemas.Membre.COLUMN_USERNAME} = '$username'"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            var id = cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_ID))
            if(cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_BOOLCLIENT))==0){
            users.add(getArtisanByID(id))}
            else {
                var artisan = Artisan(getMembreByID(id), "", "", false, false, 0.0f, "")
                users.add(artisan)
            }
        }

        cursor.close()
        db.close()

        return users
    }

    //SEARCH METHOD TO FIND USERS BY THE NUMBER OF THEIR REPORTS//
    fun reportedUsers(): List<Artisan>{
        val db = readableDatabase
        val reportedUsers = mutableListOf<Artisan>()
        val query = "SELECT * FROM ${Table_Schemas.Membre.TABLE_NAME}" +
                " WHERE ${Table_Schemas.Membre.COLUMN_REPORTS}>0 " +
                //to order by the highest number of reports to the lowest
                "ORDER BY ${Table_Schemas.Membre.COLUMN_REPORTS} DESC"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
                var id = cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_ID))
                if(cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_BOOLCLIENT))==0){
                    reportedUsers.add(getArtisanByID(id))}
                else {
                    var artisan = Artisan(getMembreByID(id), "", "", false, false, 0.0f, "")
                    reportedUsers.add(artisan)
                }
        }
        cursor.close()
        db.close()

        return reportedUsers
    } //none reported users is the same as getting all the users of the database.

    fun searchDemandeByAddress(address: String): List<Demande>{
        val db = readableDatabase
        val filteredDemandeAdr = mutableListOf<Demande>()
        val query = "SELECT * FROM ${Table_Schemas.Demandes.TABLE_NAME}" +
                " WHERE ${Table_Schemas.Demandes.COLUMN_ADDRESS} = '$address'"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            filteredDemandeAdr.add(Demande(
                cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_NUM_DEMANDE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_ID_CLIENT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_REGION)),
                address,
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_CATEGORIE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_SERVICE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_HOUR)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_URGENT))==1,
                cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_MATERIAL_INCLUDED))==1)
            )
        }

        cursor.close()
        db.close()

        return filteredDemandeAdr
    }


    //END OF ALL SEARCH METHODS
    //=======================================================================================
    //START OF DEMANDE + TASKS + RENDEZ-VOUS GETTERS & SETTERS

    //METHOD TO RETURN ALL THE EXISTING REQUESTS TAKING IN CONSIDERATION
    //WITHER THE ARTISAN IS DISPONIBLE OR NOT
    fun getDemande(num_demande : Int): Demande{
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Demandes.TABLE_NAME} WHERE ${Table_Schemas.Demandes.COLUMN_NUM_DEMANDE} = $num_demande"
        val cursor = db.rawQuery(query, null)

        cursor.moveToFirst()

        val demande = Demande(num_demande,
            cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_ID_CLIENT)),
            cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_TITLE)),
            cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_DESCRIPTION)),
            cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_REGION)),
            cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_ADDRESS)),
            cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_CATEGORIE)),
            cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_SERVICE)),
            cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_DATE)),
            cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_HOUR)),
            cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_URGENT))==1,
            cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_MATERIAL_INCLUDED))==1
            )

        cursor.close()
        db.close()
        return demande
    }
    fun getAllDemandeByRegionDispo(region: String, dispo: Boolean): List<Demande>{
        val demande = mutableListOf<Demande>()
        val db = readableDatabase

        //IF THE ARTISAN DISPONIBLE THE URGENT REQUESTS ARE SHOWN, OTHERWISE ONLY THE NONE URGENT IN THE SAME REGION AS THE ONE HE HAS IN
        // HIS PROFILE #WORK_AREA#

        //for disponible
        val query1 = "SELECT * FROM ${Table_Schemas.Demandes.TABLE_NAME} WHERE ${Table_Schemas.Demandes.COLUMN_REGION} = '$region' AND ${Table_Schemas.Demandes.COLUMN_CONFIRMED} = 0"
        //for not disponible
        val query2 = "SELECT * FROM ${Table_Schemas.Demandes.TABLE_NAME} WHERE ${Table_Schemas.Demandes.COLUMN_REGION} = '$region' AND ${Table_Schemas.Demandes.COLUMN_URGENT} = 0 AND ${Table_Schemas.Demandes.COLUMN_CONFIRMED} = 0"

        val cursor: Cursor

        if(dispo){cursor = db.rawQuery(query1, null)}
        else cursor= db.rawQuery(query2, null)

        while (cursor.moveToNext()){

            //getting the requests in the list
            demande.add(Demande(
                cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_NUM_DEMANDE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_ID_CLIENT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_REGION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_ADDRESS)),
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_CATEGORIE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_SERVICE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_HOUR)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_URGENT))==1,
                cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_MATERIAL_INCLUDED))==1)
            )
        }
        cursor.close()
        db.close()
        return demande
    }

    //RETURNS ALL THE TASKS OF THE ARTISANS
    fun getTasksArtisan(artisanId: Int): List<RendezVousTasks>{ //with history, all tasks including the completed ones for the history
        val tasks = mutableListOf<RendezVousTasks>()
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Tasks_Rendez.TABLE_NAME} WHERE" +
                " ${Table_Schemas.Tasks_Rendez.COLUMN_ID_ARTISAN} = $artisanId"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            tasks.add(
                RendezVousTasks(
                cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Tasks_Rendez.COLUMN_ID_CLIENT)),
                artisanId,
                cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Tasks_Rendez.COLUMN_NUM_DEMANDE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Tasks_Rendez.COLUMN_COMPLETED))==1
            )
            )
        }
        cursor.close()
        db.close()
        return tasks
    }


    //RETURNS ALL THE RENDEZ-VOUS OF THE CLIENT
    fun getRendezVousClient(clientId: Int): List<RendezVousTasks>{ //no history if the rendezvous is done, it won't be returned here
        val rendezVousTasks = mutableListOf<RendezVousTasks>()
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Tasks_Rendez.TABLE_NAME} WHERE ${Table_Schemas.Tasks_Rendez.COLUMN_ID_CLIENT} = $clientId AND " +
                "${Table_Schemas.Tasks_Rendez.COLUMN_COMPLETED} = 0"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            rendezVousTasks.add(RendezVousTasks(
                clientId,
                cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Tasks_Rendez.COLUMN_ID_ARTISAN)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Tasks_Rendez.COLUMN_NUM_DEMANDE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Tasks_Rendez.COLUMN_COMPLETED))==1
            ))
        }
        cursor.close()
        db.close()
        return rendezVousTasks
    }

    //METHOS TO ADD A NEW REQUEST BY THE CLIENT
    fun addDemande(demande: Demande){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Table_Schemas.Demandes.COLUMN_ID_CLIENT, demande.id_client)
            put(Table_Schemas.Demandes.COLUMN_TITLE, demande.title)
            put(Table_Schemas.Demandes.COLUMN_DESCRIPTION, demande.description)
            put(Table_Schemas.Demandes.COLUMN_REGION, demande.region)
            put(Table_Schemas.Demandes.COLUMN_ADDRESS, demande.address)
            put(Table_Schemas.Demandes.COLUMN_CATEGORIE, demande.categorie)
            put(Table_Schemas.Demandes.COLUMN_SERVICE, demande.service)
            put(Table_Schemas.Demandes.COLUMN_DATE, demande.date)
            put(Table_Schemas.Demandes.COLUMN_HOUR, demande.hour)
            put(Table_Schemas.Demandes.COLUMN_URGENT, demande.urgent)
            put(Table_Schemas.Demandes.COLUMN_MATERIAL_INCLUDED, demande.material)
            put(Table_Schemas.Demandes.COLUMN_CONFIRMED, false)
        }

        db.insert(Table_Schemas.Demandes.TABLE_NAME, null, values)
        db.close()
    }


    //METHOD TO RETURN THE RENDEZ-VOUS OF THE CLIENT BY THE CHOSEN CATEGORIES WITHOUT THE COMPLETED ONES
    fun filterRendezVousByCategorie(clientId: Int, categorie: String): List<Demande>{

        // quand il clique sur sur une image d'une category

        val db = readableDatabase
        val rendezvousCategorie = mutableListOf<Demande>()

        val query = "SELECT * FROM ${Table_Schemas.Tasks_Rendez.TABLE_NAME} " +
                "WHERE ${Table_Schemas.Tasks_Rendez.COLUMN_ID_CLIENT} = $clientId" +
                " AND ${Table_Schemas.Tasks_Rendez.COLUMN_COMPLETED} =0"

        val  cursorCategorie= db.rawQuery(query, null)

        while (cursorCategorie.moveToNext()){

            var num_demande = cursorCategorie.getInt(cursorCategorie.getColumnIndexOrThrow(Table_Schemas.Tasks_Rendez.COLUMN_NUM_DEMANDE))

            var queryDemande = "SELECT * FROM ${Table_Schemas.Demandes.TABLE_NAME} " +
                    "WHERE ${Table_Schemas.Demandes.COLUMN_NUM_DEMANDE} = $num_demande "

            var  cursor= db.rawQuery(queryDemande, null)

            cursor.moveToFirst()
            if (cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_CATEGORIE)).toString()==categorie){
                rendezvousCategorie.add(Demande(
                    num_demande,
                    clientId,
                    cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_REGION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_ADDRESS)),
                    categorie,
                    cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_SERVICE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_HOUR)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_URGENT))==1,
                    cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_MATERIAL_INCLUDED))==1)
                )
            }//end if
            cursor.close()
        }//end while

        cursorCategorie.close()
        db.close()
        return rendezvousCategorie

    }

    fun getAllDemande(): List<String>{
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Demandes.TABLE_NAME}"
        val cursor = db.rawQuery(query, null)

        val list = mutableListOf<String>()

        while (cursor.moveToNext()){
            list.add(
                cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Demandes.COLUMN_DATE))
            )
        }

        cursor.close()
        db.close()
        return list
    }

    //METHOD TO SET THE DEMANDE TO COMPLETED
    fun setTaskCompleted(num_demande: Int){
        val db = writableDatabase
        val query = "UPDATE ${Table_Schemas.Tasks_Rendez.TABLE_NAME}" +
                " SET ${Table_Schemas.Tasks_Rendez.COLUMN_COMPLETED}=?" +
                " WHERE ${Table_Schemas.Tasks_Rendez.COLUMN_NUM_DEMANDE} = ?"

        db.execSQL(query, arrayOf(1, num_demande))
    }
    //END OF DEMANDE GETTERS AND SETTERS

    //============================================================================================
    //START COMMENT AND NOTATION METHODS
    //==========================================================================================


    //METHODS TO SEE IF THE COMMENTER ALREADY EXISTS IN THE ARTISAN SECTION
    fun commenterExist(commenterId: Int, artisanId: Int): Boolean{
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Comments.TABLE_NAME} WHERE " +
                "${Table_Schemas.Comments.COLUMN_ID_ARTISAN} = $artisanId AND " +
                "${Table_Schemas.Comments.COLUMN_ID_COMMENTER} = $commenterId"
        val cursor = db.rawQuery(query, null)

        val bool = cursor.count>0
        cursor.close()

        return bool

    }

    //METHOD TO ADD A COMMENT
    fun addComment(artisanId: Int, commenterId: Int, commentText: String){
        val db = writableDatabase

        if(!commenterExist(commenterId, artisanId)){
        val values = ContentValues().apply {
            put(Table_Schemas.Comments.COLUMN_ID_ARTISAN, artisanId)
            put(Table_Schemas.Comments.COLUMN_ID_COMMENTER, commenterId)
            put(Table_Schemas.Comments.COLUMN_COMMENT, commentText)
            put(Table_Schemas.Comments.COLUMN_NOTATION, 0.0)
        }
            db.insert(Table_Schemas.Comments.TABLE_NAME, null, values)
        }else{
            val query = "UPDATE ${Table_Schemas.Comments.TABLE_NAME} " +
                    "SET ${Table_Schemas.Comments.COLUMN_COMMENT} = '$commentText' WHERE " +
                    "${Table_Schemas.Comments.COLUMN_ID_COMMENTER} = $commenterId" +
                    " AND ${Table_Schemas.Comments.COLUMN_ID_ARTISAN} = $artisanId"

            db.execSQL(query, null)
        }

        db.close()
    }

    //METHOD TO ADD A RATING
    fun addRating(artisanId: Int, commenterId: Int, notation: Float){
        val db = writableDatabase

        if (!commenterExist(commenterId, artisanId)){
            //if the comment doesn't exist
            val values = ContentValues().apply {
                put(Table_Schemas.Comments.COLUMN_ID_ARTISAN, artisanId)
                put(Table_Schemas.Comments.COLUMN_ID_COMMENTER, commenterId)
                put(Table_Schemas.Comments.COLUMN_NOTATION, notation)

         }
            db.insert(Table_Schemas.Comments.TABLE_NAME, null, values)
        }else{
            //if the commenter exists
            val query = "UPDATE ${Table_Schemas.Comments.TABLE_NAME} " +
                    "SET ${Table_Schemas.Comments.COLUMN_NOTATION} = $notation WHERE " +
                    "${Table_Schemas.Comments.COLUMN_ID_COMMENTER} = $commenterId" +
                    " AND ${Table_Schemas.Comments.COLUMN_ID_ARTISAN} = $artisanId"

            db.execSQL(query, null)
        }
        db.close()
    }

    //METHOD TO REATURN ALL THE COMMENTS OF AN ARTISAN PROFILE
    fun getAllArtisanComments(artisanId: Int): List<Comment>{
        val comments = mutableListOf<Comment>()
        val db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Comments.TABLE_NAME} " +
                "WHERE ${Table_Schemas.Comments.COLUMN_ID_ARTISAN} = $artisanId"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            comments.add(
                Comment(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Comments.COLUMN_ID_COMMENTER)),
                    artisanId,
                    cursor.getString(cursor.getColumnIndexOrThrow(Table_Schemas.Comments.COLUMN_COMMENT)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(Table_Schemas.Comments.COLUMN_NOTATION))
                )
            )
        }

        cursor.close()
        db.close()

        return comments
    }

    fun reportUser(id: Int){
        var db = readableDatabase
        val query = "SELECT * FROM ${Table_Schemas.Membre.TABLE_NAME} WHERE ${Table_Schemas.Membre.COLUMN_ID} = $id"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        var rp = cursor.getInt(cursor.getColumnIndexOrThrow(Table_Schemas.Membre.COLUMN_REPORTS))
        rp++
        db = writableDatabase
        val query2 = "UPDATE ${Table_Schemas.Membre.TABLE_NAME} SET ${Table_Schemas.Membre.COLUMN_REPORTS} = ?" +
                " WHERE ${Table_Schemas.Membre.COLUMN_ID} = ?"
        db.execSQL(query2, arrayOf(rp, id))

        cursor.close()
        db.close()

    }

    //END COMMENT & NOTATION METHODS//
    //===========================================================================================
}