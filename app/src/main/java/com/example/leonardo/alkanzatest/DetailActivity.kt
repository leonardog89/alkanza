package com.example.leonardo.alkanzatest

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import io.realm.RealmResults

import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() ,IRepo{

    override fun successPlaces(placesResult: ArrayList<String>?) {
        adapter!!.changeData(placesResult!!)
    }

    var places:  ArrayList<String>? = ArrayList()
    var adapter:AnimalAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var repo=RepoPlaces()
        repo.setListener(this)
        repo.getPlaces()
       // places!!.add("place")
        recycler.layoutManager = LinearLayoutManager(this)
        adapter= AnimalAdapter(places!!, this)
        recycler.adapter =adapter


    }
}
