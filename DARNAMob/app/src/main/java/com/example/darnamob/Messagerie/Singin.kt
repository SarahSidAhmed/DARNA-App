import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class SigninActivity : AppCompatActivity() {
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var signinBtn: TextView
    private lateinit var signupBtn: TextView
    private var email: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        userEmail = findViewById(R.id.emailtext)
        userPassword = findViewById(R.id.passwordtext)
        signinBtn = findViewById(R.id.login)
        signupBtn = findViewById(R.id.signup)

        signinBtn.setOnClickListener {
            email = userEmail.text.toString().trim()
            password = userPassword.text.toString().trim()
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
            signin()
        }

        signupBtn.setOnClickListener {
            val intent = Intent(this@SigninActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this@SigninActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun signin() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val username = FirebaseAuth.getInstance().currentUser?.displayName
                val intent = Intent(this@SigninActivity, MainActivity::class.java)
                intent.putExtra("name", username)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                if (e is FirebaseAuthInvalidUserException) {
                    Toast.makeText(this@SigninActivity, "User does not exist", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SigninActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}