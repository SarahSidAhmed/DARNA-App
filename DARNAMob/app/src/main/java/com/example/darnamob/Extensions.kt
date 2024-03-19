package com.example.darnamob

import java.security.MessageDigest


//for security of the database and the accounts of the user
fun String.toSHA256(): String{
    val HEX_CHARS = "0123456789ABCDEF"
    val digest = MessageDigest.getInstance("SHA-256").digest(this.toByteArray())
    return digest.joinToString(
        separator = "",
        transform = { a->
            String(
                charArrayOf(
                    HEX_CHARS[a.toInt() shr 4 and 0x0f],
                    HEX_CHARS[a.toInt() and 0x0f]
                )
            )
        }
    )
}
