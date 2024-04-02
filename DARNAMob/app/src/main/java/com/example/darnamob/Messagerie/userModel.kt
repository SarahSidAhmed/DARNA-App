
package com.example.darnamob.Accueil
class UserModel(
    val userID: String,
    val userName: String,
    val userEmail: String,
    val userPassword: String
) {
    // Getters for all properties
    fun getUserID(): String {
        return userID
    }

    fun getUserName(): String {
        return userName
    }

    fun getUserEmail(): String {
        return userEmail
    }

    fun getUserPassword(): String {
        return userPassword
    }
}