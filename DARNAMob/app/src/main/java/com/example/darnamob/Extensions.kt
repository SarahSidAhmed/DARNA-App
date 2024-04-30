package com.example.darnamob

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
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

fun imageFromDrawableToByteArray(context: Context, drawableId: Int): ByteArray {
    val drawable = context.resources.getDrawable(drawableId, null) // Get the drawable
    val bitmap = BitmapFactory.decodeResource(context.resources, drawableId) // Convert drawable to Bitmap
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream) // Compress bitmap to JPEG format
    return stream.toByteArray() // Convert compressed bitmap to byte array
}



fun nightTime(hour: String):Boolean{
    val time = hour.toInt()
    return time>16 || time<0
}
