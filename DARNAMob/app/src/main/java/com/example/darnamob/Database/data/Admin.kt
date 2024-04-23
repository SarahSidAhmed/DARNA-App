package com.example.darnamob.Database.data

import android.provider.ContactsContract.Data
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.Database.Table_Schemas

data class Admin(val id : Int,
                 var email : String,
                 var password : String)
