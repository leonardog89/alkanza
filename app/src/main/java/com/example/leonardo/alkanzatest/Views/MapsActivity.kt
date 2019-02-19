package com.example.leonardo.alkanzatest.Views

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.leonardo.alkanzatest.*
import com.example.leonardo.alkanzatest.Entity.PlaceResult
import com.example.leonardo.alkanzatest.Utils.IProxy
import com.example.leonardo.alkanzatest.Utils.Proxy
import com.example.leonardo.alkanzatest.Utils.RepoMinimun
import com.example.leonardo.alkanzatest.Utils.RepoRealm
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
    var repoRealm: RepoRealm?=null
    var radio :String?= null

    override fun onMapClick(p0: LatLng?) {

        mMap.clear()
        pos=p0
        mMap.addMarker(MarkerOptions().position(p0!!).title("Center").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
        text.visibility= View.GONE
    }

    override fun successPlaces(placesResult: Array<PlaceResult>?) {



        if(placesResult!=null) {

            val locationA = Location("center")

            locationA.setLatitude(pos!!.latitude)
            locationA.setLongitude(pos!!.longitude)

            val distanceVector = ArrayList<Int>()

            for (it in placesResult) {
                val position = LatLng(it.geometry?.location?.lat!!, it.geometry?.location?.lng!!)
                val locationB = Location("punto B")
                locationB.setLatitude(position.latitude)
                locationB.setLongitude(position.longitude)
                val distance = locationA.distanceTo(locationB).toInt()
                distanceVector.add(distance)
            }
            var calculation = RepoMinimun().calculateMinimun(distanceVector)
            Toast.makeText(this@MapsActivity, "desbalance mimimo: "+calculation.minumum, Toast.LENGTH_SHORT).show()
            var searchId= repoRealm?.saveSearch(radio!!, pos!!.latitude,pos!!.latitude,calculation.minumum)
            for (it in placesResult.withIndex()) {
                it.value
                val name = it.value.name
                val position = LatLng(it.value.geometry?.location?.lat!!, it.value.geometry?.location?.lng!!)
                if(it.index>=calculation.pos){
                    mMap.addMarker(MarkerOptions().position(position).title(name))
                }else{
                    mMap.addMarker(MarkerOptions().position(position).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
                }

                repoRealm?.savePlace(it.value.id!!, it.value.name!!, it.value.geometry?.location?.lat!!, it.value.geometry!!.location!!.lng!!,searchId!!)
            }
        }else{
            Toast.makeText(this@MapsActivity, "sin resultados", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        repoRealm= RepoRealm()


        bt_search.setOnClickListener {
            radio=editText.text.toString()
            try {
                radio!!.toInt()
                var proxy= Proxy()
                proxy.setListener(this)
                proxy.getPlaces(this,pos!!,radio!!)
            }catch (e:Exception){
                editText.error="Ingrese un entero"
            }
        }
        bt_detail.setOnClickListener {
            val intent = Intent(this, DetailSearchActivity::class.java)
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
