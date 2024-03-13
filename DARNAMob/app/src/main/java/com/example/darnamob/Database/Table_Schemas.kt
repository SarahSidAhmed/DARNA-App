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

        const val CREATE_QUERY = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_TEL TEXT ," +
                "$COLUMN_ADDRESS TEXT, $COLUMN_EMAIL TEXT UNIQUE, $COLUMN_PASSWORD TEXT, $COLUMN_USERNAME TEXT," +
                "$COLUMN_IMAGE BLOB, $COLUMN_BOOLCLIENT BOOLEAN DEFAULT 1, $COLUMN_NOTIFCHECK BOOLEAN DEFAULT 0, $COLUMN_REPORTS INTEGER DEFAULT 0)"
    }

    object Admin{
        const val TABLE_NAME = "admin"
        const val COLUMN_ID = "id"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"

        const val CREATE_QUERY = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_EMAIL TEXT UNIQUE, $COLUMN_PASSWORD TEXT)"
    }

    object Artisan{
        const val TABLE_NAME = "artisan"
        const val COLUMN_ID = "id_artisan"
        const val COLUMN_DOMAIN = "domain"
        const val COLUMN_PRESTATION = "prestation"
        const val COLUMN_DISPONIBLE = "disponible"
        const val COLUMN_DEPLACEMENT = "deplacement"
        const val COLUMN_RATING = "rating"
        const val COLUMN_WORKING_AREA = "working area"

        const val CREATE_QUERY = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY," +
                "FOREIGN KEY($COLUMN_ID) REFERENCES ${Membre.TABLE_NAME}(${Membre.COLUMN_ID}) ON DELETE CASCADE" +
                "$COLUMN_DOMAIN TEXT, $COLUMN_PRESTATION TEXT , $COLUMN_DISPONIBLE BOOLEAN DEFAULT 0, $COLUMN_DEPLACEMENT BOOLEAN DEFAULT 0, $COLUMN_RATING FLOAT, $COLUMN_WORKING_AREA TEXT)"
    }
    /*object WorkHours{
        const val TABLE_NAME = "workhours"
        const val COLUMN_ID = "id"
        const val COLUMN_HOUR = "hour"
    }*/

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

        const val CREATE_QUERY = "CREATE TABLE $TABLE_NAME ( $COLUMN_ID INTEGER PRIMARY KEY, FOREIGN KEY($COLUMN_ID) REFERENCES" +
                "${Artisan.TABLE_NAME}(${Artisan.COLUMN_ID}) ON DELETE CASCADE, " +
                "$COLUMN_MONDAY BOOLEAN DEFAULT 0, $COLUMN_TUESDAY BOOLEAN DEFAULT 0,$COLUMN_WEDNESDAY BOOLEAN DEFAULT 0,$COLUMN_THURSDAY BOOLEAN DEFAULT 0," +
                "$COLUMN_FRIDAY BOOLEAN DEFAULT 0,$COLUMN_SATURDAY BOOLEAN DEFAULT 0,$COLUMN_SUNDAY BOOLEAN DEFAULT 0)"
    }

    object Comments{
        const val TABLE_NAME = "comments"
        const val COLUMN_ID_ARTISAN = "id_artisan"
        const val COLUMN_ID_COMMENTER = "id+commenter"
        const val COLUMN_COMMENT = "comment"
        const val COLUMN_NOTATION = "notation"

        const val CREATE_QUERY = "CREATE TABLE $TABLE_NAME ($COLUMN_ID_ARTISAN INTEGER PRIMARY KEY, FOREIGN KEY($COLUMN_ID_ARTISAN) REFERENCES " +
                "${Artisan.TABLE_NAME}(${Artisan.COLUMN_ID}) ON DELETE CASCADE, $COLUMN_ID_COMMENTER INTEGER," +
                "FOREIGN KEY($COLUMN_ID_COMMENTER) REFERENCES ${Membre.TABLE_NAME} (${Membre.COLUMN_ID}) ON DELETE CASCADE," +
                "$COLUMN_COMMENT TEXT , $COLUMN_NOTATION INTEGER)"
    }

}