package com.example.leonardo.alkanzatest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,IProxy,GoogleMap.OnMapClickListener {

    var pos :LatLng?=null
    private lateinit var mMap: GoogleMap
    var repoPlaces:RepoPlaces?=null

    override fun onMapClick(p0: LatLng?) {

        mMap.clear()
        pos=p0
        mMap.addMarker(MarkerOptions().position(p0!!).title("Center"))
        text.visibility= View.GONE
    }

    override fun successPlaces(placesResult: Array<PlaceResult>?) {

        placesResult?.forEach {
            val name=it.name
            val position = LatLng(it.geometry?.location?.lat!!,it.geometry?.location?.lng!! )
            mMap.addMarker(MarkerOptions().position(position).title(name))
            repoPlaces!!.savePlace(it.id!!,it.name!!,it.geometry!!.location!!.lat!!,it.geometry!!.location!!.lng!!)
        }
        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        repoPlaces= RepoPlaces()


        button.setOnClickListener {
            var radio=editText.text.toString()
            try {
                radio.toInt()
                var proxy=Proxy()
                proxy.setListener(this)
                proxy.getPlaces(this,pos!!,radio)
            }catch (e:Exception){
                editText.error="Ingrese un entero"
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        var alcanza=LatLng(4.6689152,-74.0526913)
        mMap = googleMap
        mMap.setOnMapClickListener(this)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(alcanza, 15F))
   }

}
