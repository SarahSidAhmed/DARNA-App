import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.darnamob.Accueil.UserModel
import com.example.darnamob.MainActivity
import com.example.darnamob.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    private lateinit var userName: EditText
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var signinBtn: TextView
    private lateinit var signupBtn: TextView
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        userName = findViewById(R.id.usernametext)
        userEmail = findViewById(R.id.emailtext)
        userPassword = findViewById(R.id.passwordtext)
        signinBtn = findViewById(R.id.login)
        signupBtn = findViewById(R.id.signup)
        databaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        signupBtn.setOnClickListener {
            name = userName.text.toString().trim()
            email = userEmail.text.toString().trim()
            password = userPassword.text.toString().trim()
            if (TextUtils.isEmpty(name)) {
                userName.error = "Please enter your name"
                userName.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(email)) {
                userEmail.error = "Please enter your email"
                userEmail.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                userPassword.error = "Please enter your password"
                userPassword.requestFocus()
                return@setOnClickListener
            }
            signup()
        }

        signinBtn.setOnClickListener {
            val intent = Intent(this@SignupActivity, SigninActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this@SignupActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun signup() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val userProfileChangeRequest = UserProfileChangeRequest.Builder().setDisplayName(name).build()
                val firebaseUser = FirebaseAuth.getInstance().currentUser
                firebaseUser?.updateProfile(userProfileChangeRequest)

                val userModel = UserModel(FirebaseAuth.getInstance().uid ?: "", name, email, password)
                databaseReference.child(FirebaseAuth.getInstance().uid ?: "").setValue(userModel)

                val intent = Intent(this@SignupActivity, MainActivity::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@SignupActivity, "Signup failed", Toast.LENGTH_SHORT).show()
            }
    }
}
