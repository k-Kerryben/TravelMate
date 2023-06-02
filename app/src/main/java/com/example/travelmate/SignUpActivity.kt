package com.example.travelmate
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
//    lateinit var edtEmail:EditText
//    lateinit var edtpassword:EditText
//    lateinit var btnRegister:Button
//    lateinit var tvlogin:TextView
    lateinit var progress: ProgressDialog
    lateinit var mAuth:FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val mEmail = findViewById<EditText>(R.id.email)
        val muser = findViewById<EditText>(R.id.username)
        val mpass = findViewById<EditText>(R.id.password)
        val mrpass = findViewById<EditText>(R.id.rpassword)
        val mcreate = findViewById<Button>(R.id.signupbtn)
        val mlogin = findViewById<Button>(R.id.signinbtn)
        mAuth= FirebaseAuth.getInstance()
        progress=ProgressDialog(this )
        progress.setTitle("loading")
        progress.setMessage("Please wait....")
        mcreate.setOnClickListener{
            // start by receiving data from user
            var email = mEmail.text.toString().trim()
            var password = mpass.text.toString().trim()
            var rpassword = mrpass.text.toString().trim()
            var user = muser.text.toString().trim()

            // check if user is submitting empty field
            if (email.isEmpty()){
                mEmail.error = "please fill this input"
                mEmail.requestFocus()
            }else if (password.isEmpty()){
                mpass.error = "please fill this input"
                mpass.requestFocus()
            }else if (user.isEmpty()){
                muser.error = "please fill this input"
                muser.requestFocus()
            }else if (rpassword.isEmpty()){
                mrpass.error = "please fill this input"
                mrpass.requestFocus()}
            else if (rpassword != password){mrpass.error = "Passwords do not match!!"
            }else if (password.length < 6){
                mpass.error = "Password less than six characters!!"
                mpass.requestFocus()
            }

            else{
                // proceed to register the user
                progress.show()
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{

                    progress.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this, "Registration Successfull 🥳🥳", Toast.LENGTH_SHORT).show()
                        //mAuth.signOut()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }else{
                        displayMessage("ERROR", it.exception!!.message.toString())
                    }
                }
            }

        }
         mlogin.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    private fun displayMessage(title:String, message:String){
        var alertDialog=AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("ok", null )
        alertDialog.create().show()
    }
}