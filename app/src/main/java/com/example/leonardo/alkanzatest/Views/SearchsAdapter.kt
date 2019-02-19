package com.example.leonardo.alkanzatest.Views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.leonardo.alkanzatest.R
import com.example.leonardo.alkanzatest.Utils.searchDto
import kotlinx.android.synthetic.main.item_list.view.*

class SearchsAdapter(var items : ArrayList<searchDto>, val context: Context,val listener:OnItemClickListener) : RecyclerView.Adapter<SearchViewHolder>() {

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder?.tvPlaceName?.text = "radio: "+items.get(position).radio+" desbalance: "+items.get(position).minumum
        holder?.tvPlaceName.setOnClickListener {
            listener.onItemClick( holder.adapterPosition) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

            val itemView= LayoutInflater.from(context).inflate(
                R.layout.item_list,
                parent,
                false
            )
//        itemView.setOnClickListener {
//           listener.onItemClick(1)
//        }
       return SearchViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    open fun changeData(items : ArrayList<searchDto>){
        this.items=items
        notifyDataSetChanged()
    }

}

class SearchViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvPlaceName = view.tv_name
}

interface OnItemClickListener {
    fun onItemClick(pos : Int)
}