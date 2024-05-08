package com.example.darnamob.Database

class Table_Schemas {
    object Membre{
        const val TABLE_NAME = "membre"
        const val COLUMN_ID = "id"
        const val COLUMN_TEL = "telephone"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_BOOLCLIENT = "boolclient"
        const val COLUMN_NOTIFCHECK = "notifcheck"
        const val COLUMN_REPORTS = "reports"

        const val CREATE_QUERY = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $COLUMN_TEL TEXT ," +
                "$COLUMN_ADDRESS TEXT," +
                " $COLUMN_EMAIL TEXT UNIQUE," +
                " $COLUMN_PASSWORD TEXT," +
                " $COLUMN_USERNAME TEXT," +
                "$COLUMN_IMAGE BLOB," +
                " $COLUMN_BOOLCLIENT BOOLEAN DEFAULT 1," +
                " $COLUMN_NOTIFCHECK BOOLEAN DEFAULT 0," +
                " $COLUMN_REPORTS INTEGER DEFAULT 0)"
    }

    object Admin{
        const val TABLE_NAME = "admin"
        const val COLUMN_ID = "id"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"

        const val CREATE_QUERY = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                " $COLUMN_EMAIL TEXT UNIQUE," +
                " $COLUMN_PASSWORD TEXT)"
    }

    object Artisan{
        const val TABLE_NAME = "artisan"
        const val COLUMN_ID = "id_artisan"
        const val COLUMN_DOMAIN = "domain"
        const val COLUMN_PRESTATION = "prestation"
        const val COLUMN_DISPONIBLE = "disponible"
        const val COLUMN_DEPLACEMENT = "deplacement"
        const val COLUMN_RATING = "rating"
        const val COLUMN_WORKING_AREA = "working_area"
        const val COLUMN_WORK_HOURS = "working_hours"

        const val CREATE_QUERY = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_DOMAIN TEXT," +
                "$COLUMN_PRESTATION TEXT," +
                "$COLUMN_DISPONIBLE BOOLEAN DEFAULT 0," +
                "$COLUMN_DEPLACEMENT BOOLEAN DEFAULT 0," +
                "$COLUMN_RATING FLOAT," +
                "$COLUMN_WORKING_AREA TEXT," +
                "$COLUMN_WORK_HOURS TEXT," +
                " FOREIGN KEY($COLUMN_ID) REFERENCES ${Membre.TABLE_NAME}(${Membre.COLUMN_ID}) ON DELETE CASCADE)"

    }

    object WorkDays{
        const val TABLE_NAME = "workdays"
        const val COLUMN_ID = "id_artisan"
        const val COLUMN_MONDAY = "monday"
        const val COLUMN_TUESDAY = "tuesday"
        const val COLUMN_WEDNESDAY = "wednesday"
        const val COLUMN_THURSDAY = "thursday"
        const val COLUMN_FRIDAY = "friday"
        const val COLUMN_SATURDAY = "saturday"
        const val COLUMN_SUNDAY = "sunday"

        const val CREATE_QUERY = "CREATE TABLE $TABLE_NAME ( " +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_MONDAY BOOLEAN DEFAULT 0," +
                " $COLUMN_TUESDAY BOOLEAN DEFAULT 0," +
                "$COLUMN_WEDNESDAY BOOLEAN DEFAULT 0," +
                "$COLUMN_THURSDAY BOOLEAN DEFAULT 0," +
                "$COLUMN_FRIDAY BOOLEAN DEFAULT 0," +
                "$COLUMN_SATURDAY BOOLEAN DEFAULT 0," +
                "$COLUMN_SUNDAY BOOLEAN DEFAULT 0," +
                "FOREIGN KEY($COLUMN_ID) REFERENCES ${Artisan.TABLE_NAME}(${Artisan.COLUMN_ID}) ON DELETE CASCADE)"
    }

    object Comments{
        const val TABLE_NAME = "comments"
        const val COLUMN_ID_ARTISAN = "id_artisan"
        const val COLUMN_ID_COMMENTER = "id_commenter"
        const val COLUMN_COMMENT = "comment"
        const val COLUMN_NOTATION = "notation"

        const val CREATE_QUERY = "CREATE TABLE $TABLE_NAME ($COLUMN_ID_ARTISAN INTEGER," +
                " $COLUMN_ID_COMMENTER INTEGER," +
                "$COLUMN_COMMENT TEXT ," +
                " $COLUMN_NOTATION INTEGER," +
                " FOREIGN KEY($COLUMN_ID_ARTISAN) REFERENCES ${Artisan.TABLE_NAME}(${Artisan.COLUMN_ID}) ON DELETE CASCADE," +
                "FOREIGN KEY($COLUMN_ID_COMMENTER) REFERENCES ${Membre.TABLE_NAME} (${Membre.COLUMN_ID}) ON DELETE CASCADE)"
    }

    object Demandes{
        const val TABLE_NAME = "demandes"
        const val COLUMN_NUM_DEMANDE = "num_demande"
        const val COLUMN_ID_CLIENT = "id_client"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "descriptiom"
        const val COLUMN_REGION = "region"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_CATEGORIE = "categorie"
        const val COLUMN_SERVICE = "service"
        const val COLUMN_DATE = "date"
        const val COLUMN_HOUR = "hour"
        const val COLUMN_URGENT = "urgent"
        const val COLUMN_MATERIAL_INCLUDED = "material"
        const val COLUMN_CONFIRMED = "confirmed"

        const val CREATE_QUERY = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_NUM_DEMANDE INTEGER PRIMARY KEY," +
                "$COLUMN_ID_CLIENT INTEGER," +
                "$COLUMN_TITLE TEXT," +
                "$COLUMN_DESCRIPTION TEXT," +
                "$COLUMN_REGION TEXT," +
                "$COLUMN_ADDRESS TEXT," +
                "$COLUMN_CATEGORIE TEXT," +
                "$COLUMN_SERVICE TEXT," +
                "$COLUMN_DATE TEXT," +
                "$COLUMN_HOUR TEXT," +
                "$COLUMN_URGENT BOOLEAN DEFAULT 0," +
                "$COLUMN_MATERIAL_INCLUDED BOOLEAN DEFAULT 0," +
                "$COLUMN_CONFIRMED BOOLEAN DEFAULT 0,"+
                "FOREIGN KEY ($COLUMN_ID_CLIENT) REFERENCES ${Membre.TABLE_NAME}(${Membre.COLUMN_ID}) ON DELETE CASCADE)"

    }
    object Tasks_Rendez{
        const val TABLE_NAME = "tasks_rendez"
        const val COLUMN_ID_ARTISAN = "id_artisan"
        const val COLUMN_ID_CLIENT = "id_client"
        const val COLUMN_NUM_DEMANDE = "num_demande"
        const val COLUMN_COMPLETED = "completed"

        const val CREATE_QUERY = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID_ARTISAN INTEGER," +
                "$COLUMN_ID_CLIENT INTEGER," +
                "$COLUMN_NUM_DEMANDE INTEGER PRIMARY KEY," +
                "$COLUMN_COMPLETED BOOLEAN DEFAULT 0," +
                "FOREIGN KEY ($COLUMN_ID_ARTISAN) REFERENCES ${Artisan.TABLE_NAME}(${Artisan.COLUMN_ID}) ON DELETE CASCADE," +
                "FOREIGN KEY($COLUMN_NUM_DEMANDE) REFERENCES ${Demandes.TABLE_NAME}(${Demandes.COLUMN_NUM_DEMANDE}) ON DELETE CASCADE," +
                "FOREIGN KEY($COLUMN_ID_CLIENT) REFERENCES ${Membre.TABLE_NAME}(${Membre.COLUMN_ID}) ON DELETE CASCADE)"

    }



    object Notification{

        const val TABLE_NAME = "notification"
        const val COLUMN_ID_RECEIVER = "id_receiver"
        const val COLUMN_ID_SENDER = "id_sender"
        const val COLUMN_NUM_DEMANDE = "num_demande"
        const val COLUMN_TYPE = "type"
        const val COLUMN_CLICKED = "clicked"
        const val COLUMN_CONFIRMED = "confirmed"

        const val CREATE_QUERY = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID_RECEIVER INTEGER," +
                "$COLUMN_ID_SENDER INTEGER," +
                "$COLUMN_NUM_DEMANDE INTEGER," +
                "$COLUMN_TYPE INTEGER," +  //there are 4 types : 0-> warning from admin
                                        //                       1-> notification of type confirm request or decline (client only)
                                        //                       2-> notification d'acception de la part d'un client (artisan)
                                        //                       3-> notification to rate the artisan (client)

                "$COLUMN_CLICKED BOOLEAN DEFAULT 0," +
                "$COLUMN_CONFIRMED BOOLEAN,"+
                "FOREIGN KEY($COLUMN_ID_RECEIVER) REFERENCES ${Membre.TABLE_NAME}(${Membre.COLUMN_ID}) ON DELETE CASCADE," +
                "FOREIGN KEY($COLUMN_ID_SENDER) REFERENCES ${Membre.TABLE_NAME}(${Membre.COLUMN_ID}) ON DELETE CASCADE," +
                "FOREIGN KEY($COLUMN_NUM_DEMANDE) REFERENCES ${Demandes.TABLE_NAME}(${Demandes.COLUMN_NUM_DEMANDE}) ON DELETE CASCADE)"
    }


    object Prestation{
        const val TABLE_NAME = "prestation"
        const val COLUMN_PRESTAT = "prestat"
        const val COLUMN_DOMAINE = "domaine"
        const val COLUMN_PRICE = "price"
        const val COLUMN_DURATION = "duration"
        const val COLUMN_MATERIALS = "materials"

        const val CREATE_QUERY = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_PRESTAT TEXT PRIMARY KEY, " +
                "$COLUMN_DOMAINE TEXT," +
                "$COLUMN_PRICE INTEGER," +
                "$COLUMN_DURATION INTEGER, " +
                "$COLUMN_MATERIALS TEXT)"
    }

    //for the domain we can just use the presation and excute the query //
    //SELECT DISTINCT Prestation.COLUMN_DOMAINE FROM Prestation.TABLE_NAME
}