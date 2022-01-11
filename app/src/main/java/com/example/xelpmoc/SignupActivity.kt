package com.example.xelpmoc

import android.content.ContentValues.TAG
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

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val signupButton = findViewById<Button>(R.id.signupbutton)
        val signupEmail = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val signupPass = findViewById<EditText>(R.id.editTextTextPassword)
        val toLogin = findViewById<TextView>(R.id.toLogin)

        toLogin.setOnClickListener {
            onBackPressed()
        }

        signupButton.setOnClickListener {
            when {
                TextUtils.isEmpty(signupEmail.text.toString().trim { it <=' '}) -> {
                    Toast.makeText(this, "Please enter email",Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(signupPass.text.toString().trim { it <=' '}) -> {
                    Toast.makeText(this, "Please enter password",Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email: String = signupEmail.text.toString().trim { it <=' '}
                    val password: String = signupPass.text.toString().trim { it <=' '}

                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) {task ->
                            if(task.isSuccessful) {
                                val user = auth.currentUser

                                user?.sendEmailVerification()
                                    ?.addOnCompleteListener(this) { task ->
                                        if(task.isSuccessful) {
                                            Toast.makeText(this, "Registered successfully. Please check your email for verification", Toast.LENGTH_SHORT).show()
                                            val intent = Intent(this, LoginActivity::class.java)
                                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            if (user != null) {
                                                intent.putExtra("user_id", user.uid)
                                            }
                                            intent.putExtra("email_id", email)
                                            startActivity(intent)
                                            finish()
                                        }
                                    }
                            }
                            else {
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }

    }
}