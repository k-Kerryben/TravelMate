package com.example.travelmate

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException

class AddDestinationActivity : AppCompatActivity() {
    private lateinit var idestination:EditText
    private lateinit var iduration:EditText
    private lateinit var iweather:EditText
    private lateinit var add_img:ImageView
    private lateinit var upload_btn:Button
    private lateinit var progress:ProgressDialog
    val img_request = 100
    private lateinit var file_path:Uri
    lateinit var firebase_store:FirebaseStorage
    lateinit var storage_reference:StorageReference
    lateinit var fAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_destination)

        idestination = findViewById(R.id.destination)
        iduration = findViewById(R.id.duration)
        iweather = findViewById(R.id.weather)
        add_img = findViewById(R.id.add_image)
        upload_btn = findViewById(R.id.upload)
        progress = ProgressDialog(this)
        progress.setTitle("Uploading")
        progress.setMessage("wait a minute..")
        firebase_store = FirebaseStorage.getInstance()
        storage_reference = firebase_store.getReference()
        fAuth = FirebaseAuth.getInstance()

        add_img.setOnClickListener {
        val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select an image "), img_request)
        }
        upload_btn.setOnClickListener {
            var iidestination = idestination.text.toString().trim()
            var iiduration = iduration.text.toString().trim()
            var iiweather = iweather.text.toString().trim()
            var imageID = System.currentTimeMillis().toString()
            var userID = fAuth.currentUser?.uid

            if (iidestination.isEmpty()){idestination.error = "Input required"}
            if (iiduration.isEmpty()){iduration.error = "Input required"}
            if (iiweather.isEmpty()){iweather.error = "Input required"}
            else{
                if (file_path == null){Toast.makeText(applicationContext, "Image Required", Toast.LENGTH_SHORT).show()}
                else{var ref = storage_reference.child()}
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == img_request && resultCode == Activity.RESULT_OK){
            if (data == null || data.data == null){
                return
            }
            file_path = data.data!!
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, file_path)
                add_img.setImageBitmap(bitmap)
            }catch (e:IOException){
                e.printStackTrace()
            }
        }
    }

}

