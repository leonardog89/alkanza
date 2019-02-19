package com.example.leonardo.alkanzatest.Views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import com.example.leonardo.alkanzatest.Utils.IRepo
import com.example.leonardo.alkanzatest.R
import com.example.leonardo.alkanzatest.Utils.RepoRealm

import kotlinx.android.synthetic.main.activity_detail.*

class DetailPlacesActivity : AppCompatActivity() , IRepo {

    override fun successPlaces(placesResult: ArrayList<String>?) {
        adapter!!.changeData(placesResult!!)
        numOfItems.text=""+placesResult!!.size
    }

    var places:  ArrayList<String>? = ArrayList()
    var adapter: PlacesAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var pos =intent.extras.getInt("pos")

        var repo= RepoRealm()
        repo.setListener(this)
        repo.getPlaces(this,pos)

        recycler.layoutManager = LinearLayoutManager(this)
        adapter= PlacesAdapter(places!!, this)
        recycler.adapter =adapter
    }
}
