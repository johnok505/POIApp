package com.jok.poiapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jok.poiapp.R
import com.jok.poiapp.mdoel.PlaceOfInterest
import com.squareup.picasso.Picasso

class PoiAdapter(
    private val poiList: List<PlaceOfInterest>
) : RecyclerView.Adapter<PoiAdapter.PoiViewHolder>() {

    var onItemClick : ((PlaceOfInterest) -> Unit)? = null

    class PoiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemImageView)
        val titleView: TextView = itemView.findViewById(R.id.itemTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.poi_list_item,
            parent,
            false
        )
        return PoiViewHolder(view)
    }

    override fun onBindViewHolder(holder: PoiViewHolder, position: Int) {
        val poi = poiList[position]
        Picasso.get().load(poi.image).into(holder.imageView)
        holder.titleView.text = poi.title

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(poi)
        }

    }

    override fun getItemCount(): Int {
        return poiList.size
    }
}