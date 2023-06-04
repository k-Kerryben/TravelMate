package com.example.travelmate

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings

class ResetActivity : AppCompatActivity() {
    private lateinit var fAuth: FirebaseAuth
    lateinit var pass:EditText
    lateinit var reset_btn:Button
    lateinit var reset_txt:TextView
    lateinit var rpass:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)

        pass = findViewById(R.id.password)
        rpass = findViewById(R.id.rpassword)
        reset_btn = findViewById(R.id.reset_btn)
        reset_txt = findViewById(R.id.reset_txt)

        reset_btn.setOnClickListener {
            resetPassword()
        }


    }

    private fun resetPassword() {

        val newPassword = pass.text.toString()
        val confirmPassword = rpass.text.toString()

        // Validate password fields
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please enter a new password and confirm it.", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword != confirmPassword) {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
            return
        }

        // Reset password and send email
        val user = fAuth.currentUser
        user?.let {
            user.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sendPasswordResetEmail(user.email.toString())
                        showNotification("Password Reset", "A password reset email has been sent to ${user.email}.")
                    } else {
                        Toast.makeText(this, "Failed to reset password: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun sendPasswordResetEmail(email: String) {
        val actionCodeSettings = actionCodeSettings {
            url = "https://yourwebsite.com/reset-password"
            handleCodeInApp = true
        }

        fAuth.sendPasswordResetEmail(email, actionCodeSettings)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Password reset email sent successfully
                } else {
                    Toast.makeText(this, "Failed to send password reset email: ${task.exception?.message}", Toast.LENGTH_SHORT)






                }

            }
    }


    private fun showNotification(title: String, message: String) {
        // Create a notification channel (required for Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "default_channel_id"
            val channelName = "Default Channel"
            val notificationManager = getSystemService(NotificationManager::class.java)
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager?.createNotificationChannel(channel)
        }

        // Create and display the notification
        val notification = NotificationCompat.Builder(this, "default_channel_id")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(0, notification)
    }


}