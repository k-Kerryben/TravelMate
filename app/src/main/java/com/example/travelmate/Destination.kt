package com.example.travelmate

class Destination {
    var destination:String = ""
    var duration:String = ""
    var weather:String = ""
    var userID:String = ""
    var destinationID:String =""

    constructor(
        destination: String,
        duration: String,
        weather: String,
        userID: String,
        destinationID: String
    ) {
        this.destination = destination
        this.duration = duration
        this.weather = weather
        this.userID = userID
        this.destinationID = destinationID
    }
    constructor()
}