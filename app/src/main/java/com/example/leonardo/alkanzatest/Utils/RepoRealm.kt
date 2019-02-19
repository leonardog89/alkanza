package com.example.leonardo.alkanzatest.Utils

import android.app.Activity
import com.example.leonardo.alkanzatest.Entity.PlaceDao
import com.example.leonardo.alkanzatest.Entity.SearchDao
import io.realm.Realm
import io.realm.RealmResults

open class RepoRealm {

    var iRepo: IRepo?=null
    var iSearchRepo: ISearchRepo?=null

    fun setListener(iRepo: IRepo){
        this.iRepo=iRepo
    }

    fun setSearchListener(iRepo: ISearchRepo){
        this.iSearchRepo=iRepo
    }
    open fun savePlace(id: String, name: String, lat: Double, lng: Double,searchId : Int) {
        var realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val place = PlaceDao(id, name, lat, lng,searchId)
        realm.copyToRealmOrUpdate(place)
        realm.commitTransaction()
    }

    open fun getPlaces(activity: Activity,pos: Int) {
        var realm = Realm.getDefaultInstance()
        var results: RealmResults<PlaceDao>?=null
        var list = ArrayList<String>()
        realm.executeTransactionAsync {
             results = it.where(PlaceDao::class.java).equalTo("searchId",pos ).findAll()
            (results as RealmResults<PlaceDao>?)!!.forEach { list.add(it.name!!) }
            activity.runOnUiThread(
                object : Runnable {
                    override fun run() {
                        iRepo!!.successPlaces(list)
                    }
                }
            )

        }
    }
    open fun saveSearch(radio : String, lat: Double, lng: Double,minimun : Double):Int {
        var realm = Realm.getDefaultInstance()
        var currentId:Int=0
        var id=realm.where(SearchDao::class.java).max("id")
        if(id == null) {
            currentId = 1
        }else{
            currentId=(id as Long).inc().toInt()
        }
        realm.executeTransactionAsync {
            val search = SearchDao(currentId, radio, lat, lng,minimun)
            it.copyToRealmOrUpdate(search)

        }
        return currentId
    }

    open fun getSearchs(activity: Activity) {
        var realm = Realm.getDefaultInstance()
        var results: RealmResults<SearchDao>?=null
        var list = ArrayList<searchDto>()
        realm.executeTransactionAsync {
            results = it.where(SearchDao::class.java).findAll()
            (results as RealmResults<SearchDao>?)!!.forEach { list.add(searchDto(it.radio!!.toString(),it.minimum!!)) }
            activity.runOnUiThread(
                object : Runnable {
                    override fun run() {
                        iSearchRepo!!.successSearch(list)
                    }
                }
            )

        }
    }
}
data class searchDto(val radio: String, val minumum: Double)
interface IRepo {
    open fun successPlaces(placesResult: ArrayList<String>?)
}
interface ISearchRepo {
    open fun successSearch(searchsResult: ArrayList<searchDto>?)
}