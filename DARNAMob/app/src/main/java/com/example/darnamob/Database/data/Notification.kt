package com.example.darnamob.Database.data

data class Notification(val id_receiver: Int,
                        val id_sender: Int,
                        val num_demande: Int,
                        val type: Int)
//there are 4 types :    0-> warning from admin
//                       1-> notification of type confirm request or decline (client only)
//                       2-> notification d'acception de la part d'un client (artisan)
//                       3-> notification to rate the artisan (client)
