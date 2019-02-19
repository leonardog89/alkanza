package com.example.leonardo.alkanzatest.Views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import com.example.leonardo.alkanzatest.R
import com.example.leonardo.alkanzatest.Utils.ISearchRepo
import com.example.leonardo.alkanzatest.Utils.RepoRealm
import com.example.leonardo.alkanzatest.Utils.searchDto

import kotlinx.android.synthetic.main.activity_detail.*

class DetailSearchActivity : AppCompatActivity() , ISearchRepo,OnItemClickListener {
    override fun onItemClick(pos: Int) {
        val intent = Intent(this, DetailPlacesActivity::class.java)
        intent.putExtra("pos",pos+1)
        startActivity(intent)
    }
    override fun successSearch(searchResult: ArrayList<searchDto>?) {
        adapter!!.changeData(searchResult!!)
        numOfItems.text=""+searchResult!!.size
    }

    var searchs:  ArrayList<searchDto>? = ArrayList()
    var adapter: SearchsAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seaarch_detail)

        var repo= RepoRealm()
        repo.setSearchListener(this)
        repo.getSearchs(this)

        recycler.layoutManager = LinearLayoutManager(this)
        adapter= SearchsAdapter(searchs!!, this,this)
        recycler.adapter =adapter
        }
}
