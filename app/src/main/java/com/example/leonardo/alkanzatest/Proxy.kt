package com.example.leonardo.alkanzatest

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.LatLng
import com.google.gson.GsonBuilder
import org.json.JSONObject

open class Proxy {

    var iProxy:IProxy?=null

    fun setListener(iProxy:IProxy){
        this.iProxy=iProxy
    }

    open fun getPlaces(context: Context, p0: LatLng, radio :String) {

        var places:Array<PlaceResult>
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url: String = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+p0.latitude.toString()+
                ","+p0.longitude+"&radius="+radio+"&type=doctor&key=AIzaSyB8yJXK4W3KMaFa2O4dXFPTeL6_3gdlNBU"

        // Request a string response from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

                var strResp = response.toString()
                val jsonObj: JSONObject = JSONObject(strResp)

                val gsonBuilder = GsonBuilder()
                gsonBuilder.setDateFormat("M/d/yy hh:mm a")
                val gson = gsonBuilder.create()

                try {
                    places =     gson.fromJson(
                            jsonObj.getJSONArray("results").toString(),
                            Array<PlaceResult>::class.java)
                    iProxy?.successPlaces(places)

                }catch (e:Exception){
                    e.printStackTrace()
                }

            },
            Response.ErrorListener {
                error ->
                Log.e("volley error",error.networkResponse.toString())
            })
        queue.add(stringReq)
    }
}
interface IProxy {
    open fun successPlaces(placesResult: Array<PlaceResult>?)
}