package com.example.xelpmoc

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toRegister = findViewById<TextView>(R.id.toRegister)
        toRegister.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
        auth = Firebase.auth

        val loginButton = findViewById<Button>(R.id.Loginbutton)
        val loginEmail = findViewById<EditText>(R.id.editTextTextEmailAddress2)
        val loginPass = findViewById<EditText>(R.id.editTextTextPassword2)
        loginButton.setOnClickListener {
            when {
                TextUtils.isEmpty(loginEmail.text.toString().trim { it <=' '}) -> {
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(loginPass.text.toString().trim { it <=' '}) -> {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email: String = loginEmail.text.toString().trim { it <=' '}
                    val password: String = loginPass.text.toString().trim { it <=' '}

                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) {task ->
                            if(task.isSuccessful) {
                                val user = auth.currentUser
                                if (user != null) {
                                    if(user.isEmailVerified) {
                                        val intent = Intent(this, MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        if (user != null) {
                                            intent.putExtra("user_id", user.uid)
                                        }
                                        intent.putExtra("email_id", email)
                                        startActivity(intent)
                                        finish()
                                    }
                                } else {
                                    Toast.makeText(this, "Please verify your email address", Toast.LENGTH_SHORT).show()
                                }

                            }
                            else {
                                Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }
}