package com.example.leonardo.alkanzatest.Views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import com.example.leonardo.alkanzatest.Utils.IRepo
import com.example.leonardo.alkanzatest.R
import com.example.leonardo.alkanzatest.Utils.RepoPlaces

import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() , IRepo {

    override fun successPlaces(placesResult: ArrayList<String>?) {
        adapter!!.changeData(placesResult!!)
        numOfItems.text=""+placesResult!!.size
    }

    var places:  ArrayList<String>? = ArrayList()
    var adapter: AnimalAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var repo= RepoPlaces()
        repo.setListener(this)
        repo.getPlaces(this)

        recycler.layoutManager = LinearLayoutManager(this)
        adapter= AnimalAdapter(places!!, this)
        recycler.adapter =adapter
    }
}
