package com.example.leonardo.alkanzatest

import io.realm.Realm
import io.realm.RealmResults

open class RepoPlaces {

    var iRepo:IRepo?=null

    fun setListener(iRepo:IRepo){
        this.iRepo=iRepo
    }

    open fun savePlace(id: String, name: String, lat: Double, lng: Double) {
        var realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val place = PlaceDao(id,name,lat,lng)
        realm.copyToRealmOrUpdate(place)
        realm.commitTransaction()
    }

    open fun getPlaces() {
        var realm = Realm.getDefaultInstance()
        var results: RealmResults<PlaceDao>?=null
        var list = ArrayList<String>()
        realm.executeTransactionAsync {
             results = it.where(PlaceDao::class.java).findAll()
            (results as RealmResults<PlaceDao>?)!!.forEach { list.add(it.name!!) }

            iRepo!!.successPlaces(list)
        }

    }
}
interface IRepo {
    open fun successPlaces(placesResult: ArrayList<String>?)
}