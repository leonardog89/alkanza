package com.example.leonardo.alkanzatest.Entity

import com.google.gson.annotations.SerializedName


class PlaceResult {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("geometry")
    val geometry: Geometry? = null
}

class Geometry {
    @SerializedName("location")
    var location: Location? = null
}


class Location {
    @SerializedName("lat")
    var lat: Double? = null

    @SerializedName("lng")
    var lng: Double? = null
}