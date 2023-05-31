package com.example.travelmate

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
//    lateinit var Email:EditText
//    lateinit var edtPassword:EditText
//    lateinit var btnLogin:Button
//    lateinit var tvRegister:TextView
//    lateinit var tvReset:TextView
    lateinit var progress:ProgressDialog
    lateinit var mAuth:FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mEmail = findViewById<EditText>(R.id.email)
        val mpass = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.loginbtn)
        val mreset =findViewById<Button>(R.id.reset)
        val mcreate =findViewById<Button>(R.id.btnsignup)
        mAuth= FirebaseAuth.getInstance()
        progress= ProgressDialog(this)
        progress.setTitle("loading")
        progress.setMessage("Please wait....")
        val mlogin =findViewById<Button>(R.id.loginbtn)
        progress.setOnCancelListener{

        }
        mcreate.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()

        }
        mreset.setOnClickListener{

        }
        login.setOnClickListener {
            // start by receiving data from user
            var email = mEmail.text.toString().trim()
            var password = mpass.text.toString().trim()
            // check if user is submitting empty field
            if (email.isEmpty()) {
                mEmail.setError("please fill this input")
                mEmail.requestFocus()
            } else if (password.isEmpty()) {
                mpass.setError("please fill this input")
                mpass.requestFocus()
            } else {
                // proceed to register the user
                progress.show()
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    progress.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this, "Registration Successfull 🥳🥳", Toast.LENGTH_SHORT).show()
                        //mAuth.signOut()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }else{
                        displayMessage("ERROR", it.exception!!.message.toString())
                    }
                }
            }
        }

    }

    fun displayMessage(title:String, message:String){
        var alertDialog= AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("ok", null )
        alertDialog.create().show()
    }

}
