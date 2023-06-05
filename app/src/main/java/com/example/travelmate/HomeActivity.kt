package com.example.travelmate

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import com.google.firebase.database.ServerValue

class HomeActivity : AppCompatActivity() {
    lateinit var camera:ImageView
    lateinit var profile_page:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        camera = findViewById(R.id.camera)
        profile_page = findViewById(R.id.profile_page)

        profile_page.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        camera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            // Now you have the image bitmap, proceed to save it in Firebase Realtime Database
            saveImageToFirebase(imageBitmap)
        }
    }

    private fun saveImageToFirebase(imageBitmap: Bitmap) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("users/${userId}/photos")
        val storageRef = FirebaseStorage.getInstance().reference.child("users/${userId}/photos/${UUID.randomUUID()}.jpg")

        val outputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val imageData = outputStream.toByteArray()

        storageRef.putBytes(imageData)
            .addOnSuccessListener {
                // Image uploaded successfully, now save the download URL in the Realtime Database
                storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    val photoData = mapOf(
                        "imageUrl" to downloadUri.toString(),
                        "timestamp" to ServerValue.TIMESTAMP
                    )
                    databaseRef.push().setValue(photoData)
                }
            }
            .addOnFailureListener { exception ->
                // Handle the failure case


            }
    }

}