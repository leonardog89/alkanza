package com.example.leonardo.alkanzatest.Views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.leonardo.alkanzatest.R
import kotlinx.android.synthetic.main.item_list.view.*

class PlacesAdapter(var items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.tvPlaceName?.text = items.get(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list,
                parent,
                false
            )
        )
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    open fun changeData(items : ArrayList<String>){
        this.items=items
        notifyDataSetChanged()
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvPlaceName = view.tv_name
}