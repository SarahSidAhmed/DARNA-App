package com.example.darnamob.Client

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.darnamob.Database.DatabaseHelper
import com.example.darnamob.databinding.ActivityAccountClientSettingsBinding
import java.io.ByteArrayOutputStream


private lateinit var binding : ActivityAccountClientSettingsBinding
private lateinit var db: DatabaseHelper
private lateinit var phone: String
private lateinit var address: String
private var userID: Int =-1
class AccountClientSettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountClientSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DatabaseHelper(this)

        userID = intent.getIntExtra("id", -1)



        //getting the data
        val membre = db.getMembreByID(userID)

        //setting the data
        binding.artAddress.setText(membre.address)
        binding.artPhone.setText(membre.tel)


        var image = membre.image
        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)

        binding.artprofilpic.setImageBitmap(bitmap)





        //you need to add the thing to get the image from their gallery and affect it to the
        //image view so that it changes
//        binding.camera.setOnClickListener {
//            ImagePicker.with(this)
//                .crop()	    			//Crop image(Optional), Check Customization for more option
//                .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
//                .start()
//        }
//
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        binding.artprofilpic.setImageURI(data?.data)
//        binding.save.setOnClickListener {
//            address = binding.artAddress.text.toString().trim()
//            phone = binding.artPhone.text.toString().trim()
//
//            val imageUri = data?.data //getting the image
//            try {
//                // Convert image URI to byte array
//                val inputStream = contentResolver.openInputStream(imageUri!!)
//                val bitmap = BitmapFactory.decodeStream(inputStream)
//                val byteArray = bitmapToByteArray(bitmap)
//
//                //inserting the changes in the DB
//                if (byteArray.isNotEmpty()) {
//                    db.editProfileMember(userID, phone, address, byteArray)
//                    Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
//                } else Toast.makeText(
//                    this,
//                    "Infos can not be empty, check again",
//                    Toast.LENGTH_SHORT
//                ).show()
//
//
//                // Use the byte array as needed
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//
//            //updating the data in the database
//        }
//    }


    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}