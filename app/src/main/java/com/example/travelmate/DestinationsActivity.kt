package com.example.travelmate

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DestinationsActivity : AppCompatActivity() {
    lateinit var listDestination:ListView
    lateinit var destination_s:ArrayList<Destination>
    lateinit var adapter: CustomAdapter
    lateinit var progress:ProgressDialog
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinations)
        listDestination = findViewById(R.id.list_destin)
        destination_s = ArrayList()
        adapter = CustomAdapter(this, Destination)
        progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Loading")
        val ref = FirebaseDatabase.getInstance()
            .getReference().child("Destination")
        progress.show()
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                destination_s.clear()
                for (snap in snapshot.children)
                {
                    var destiny = snap.getValue(Destination::class.java)
                    destination_s.add(destiny!!)
                }
                adapter.notifyDataSetChanged()
                progress.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "DB inaccessible", Toast.LENGTH_SHORT).show()
            }
        })
        listDestination.adapter = adapter
    }
}