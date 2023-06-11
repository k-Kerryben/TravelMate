package com.example.travelmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val textViewUsername = findViewById<TextView>(R.id.name)
        val textViewEmail = findViewById<TextView>(R.id.memailt)
        val logoutbtn = findViewById<ImageView>(R.id.logout)



        val user = mAuth.currentUser
        val username = user?.displayName
        val email = user?.email

        if (!username.isNullOrEmpty()) {
            textViewUsername.text = getString(R.string.username_format, username)
        } else {
            textViewUsername.text = getString(R.string.username)
        }

        if (!email.isNullOrEmpty()) {
            textViewEmail.text = getString(R.string.email_format, email)
        } else {
            textViewEmail.text = getString(R.string.email)
        }

        logoutbtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}