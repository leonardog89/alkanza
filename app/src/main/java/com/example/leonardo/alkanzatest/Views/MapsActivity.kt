package com.example.leonardo.alkanzatest.Views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.leonardo.alkanzatest.*
import com.example.leonardo.alkanzatest.Entity.PlaceResult
import com.example.leonardo.alkanzatest.Utils.IProxy
import com.example.leonardo.alkanzatest.Utils.Proxy
import com.example.leonardo.alkanzatest.Utils.RepoPlaces
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, IProxy,GoogleMap.OnMapClickListener {

    var pos :LatLng?=null
    private lateinit var mMap: GoogleMap
    var repoPlaces: RepoPlaces?=null

    override fun onMapClick(p0: LatLng?) {

        mMap.clear()
        pos=p0
        mMap.addMarker(MarkerOptions().position(p0!!).title("Center").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
        text.visibility= View.GONE
    }

    override fun successPlaces(placesResult: Array<PlaceResult>?) {

        placesResult?.forEach {
            val name=it.name
            val position = LatLng(it.geometry?.location?.lat!!,it.geometry?.location?.lng!! )
            mMap.addMarker(MarkerOptions().position(position).title(name))
            repoPlaces!!.savePlace(it.id!!,it.name!!,it.geometry!!.location!!.lat!!,it.geometry!!.location!!.lng!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        repoPlaces= RepoPlaces()


        bt_search.setOnClickListener {
            var radio=editText.text.toString()
            try {
                radio.toInt()
                var proxy= Proxy()
                proxy.setListener(this)
                proxy.getPlaces(this,pos!!,radio)
            }catch (e:Exception){
                editText.error="Ingrese un entero"
            }
        }
        bt_detail.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        var alcanza=LatLng(4.6689152,-74.0526913)
        mMap = googleMap
        mMap.setOnMapClickListener(this)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(alcanza, 15F))
   }

}
