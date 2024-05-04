package com.example.darnamob


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date


private lateinit var db : DatabaseHelper
private lateinit var binding: ActivityMainBinding
private  var calendar = java.util.Calendar.getInstance()


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        val userId = intent
//        db = DatabaseHelper(this)
//        val email = "ms_kadid@esi.dz"
//        val pass = "1234"
//        var check = db.checkEmailPassword(email, pass)
//        if(check) Toast.makeText(this@MainActivity, "YES", Toast.LENGTH_SHORT).show()
//        else Toast.makeText(this@MainActivity, "NO", Toast.LENGTH_SHORT).show()

        val drawableName = "initprofile"
        val resId = resources.getIdentifier(drawableName, "drawable", "com.example.darnamob")

        if (resId != 0) {
            //Toast.makeText(this, "$resId", Toast.LENGTH_SHORT).show()

            val image = imageFromDrawableToByteArray(this, resId)

            if (image.isNotEmpty()) {
                Toast.makeText(this, "HEYOOO", Toast.LENGTH_SHORT).show()

                val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
                binding.image.setImageBitmap(bitmap)
            } else {
                Toast.makeText(this, "Image is empty", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "ResID unavailable", Toast.LENGTH_SHORT).show()

        }

    }

    fun imageFromDrawableToByteArray(context: Context, drawableId: Int): ByteArray {
        val drawable = context.resources.getDrawable(drawableId, null) // Get the drawable
        val bitmap = BitmapFactory.decodeResource(
            context.resources,
            drawableId
        ) // Convert drawable to Bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream) // Compress bitmap to JPEG format
        return stream.toByteArray() // Convert compressed bitmap to byte array
    }
}
