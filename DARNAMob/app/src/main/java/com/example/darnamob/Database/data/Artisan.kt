package com.example.darnamob.Database.data

data class Artisan( val membre : Membre,
                    var domain: String,
                    var prestation : String,
                    var disponible : Boolean,
                   var deplacement: Boolean,
                    var Rating: Float,
                    var work_Area : String)
