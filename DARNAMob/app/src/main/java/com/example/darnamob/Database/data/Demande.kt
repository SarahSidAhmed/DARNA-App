package com.example.darnamob.Database.data

import java.util.Date

data class Demande(val num_demande: Int, val id_client: Int, val title: String, val description: String,
                    val region: String, val address: String, val categorie: String, val service: String, val date: String,
                   val hour: String,
                    val urgent: Boolean, val material: Boolean)
