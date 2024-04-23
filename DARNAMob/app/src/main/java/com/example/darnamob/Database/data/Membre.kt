package com.example.darnamob.Database.data

data class Membre(val id:Int,
                  var tel : String,
                  var address: String,
                  var email: String,
                  var password : String,
                  var userName: String,
                  var image : ByteArray,
                  var ndReports : Int )
