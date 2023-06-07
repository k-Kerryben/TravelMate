package com.example.travelmate

import android.widget.EditText

class Destination {
    var destination:String = ""
    var duration:String = ""
    var weather:String = ""
    var userID:String = ""
    var destinationID:String =""
    var imagedes:String = ""

    constructor(
        destination: EditText,
        duration: EditText,
        weather: EditText,
        userID: String,
        destinationID: String,
        imagedes: String
    ) {
        this.destination = destination.toString()
        this.duration = duration.toString()
        this.weather = weather.toString()
        this.userID = userID
        this.destinationID = destinationID
        this.imagedes = imagedes
    }
    constructor()
}