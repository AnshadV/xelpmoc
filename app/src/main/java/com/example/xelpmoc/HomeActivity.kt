package com.example.xelpmoc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = Firebase.auth
        if(auth.currentUser != null) {
            val user = auth.currentUser
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            if (user != null) {
                intent.putExtra("user_id", user.uid)
                intent.putExtra("email_id", user.email)
            }
            startActivity(intent)
            finish()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
}