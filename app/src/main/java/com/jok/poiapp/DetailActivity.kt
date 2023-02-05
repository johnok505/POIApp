package com.jok.poiapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jok.poiapp.mdoel.PlaceOfInterest
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val poi = intent.getParcelableExtra<PlaceOfInterest>("poi")
        if (poi != null) {
            val titleTextView = findViewById<TextView>(R.id.titleTextView)
            val imageView = findViewById<ImageView>(R.id.imageView)
            val coordinatesTextView = findViewById<TextView>(R.id.coordinatesTextView)

            titleTextView.text = poi.title
            coordinatesTextView.text = poi.geocoordinates
            Picasso.get().load(poi.image).into(imageView)
        }
    }
}