package com.jok.poiapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jok.poiapp.adapter.PoiAdapter
import com.jok.poiapp.mdoel.PlaceOfInterest
import com.jok.poiapp.viewmodel.PoiViewModel

class PoiListActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val viewModel: PoiViewModel by lazy {
        ViewModelProvider(this)[PoiViewModel::class.java]
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var poiAdapter: PoiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poi_list)

        recyclerView = findViewById(R.id.poiRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)

        viewModel.getPoiList(this)?.observe(this) {
            if (it.isEmpty()) {
                viewModel.fetchPoiList(this)
                return@observe
            }
            updateRecyclerView(it)
        }
        viewModel.poiLiveData.observe(this){
            if (it == null) {
                //short toast message in case we fail to retrieve data
                Toast.makeText(this, "data not found", Toast.LENGTH_SHORT).show()
                viewModel.fetchPoiList(this)
                return@observe
            }
            updateRecyclerView(it)
        }

        }

    private fun updateRecyclerView(pois: List<PlaceOfInterest>) {

        poiAdapter = PoiAdapter(pois)
        recyclerView.adapter = poiAdapter

        poiAdapter.onItemClick = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("poi", it)
            startActivity(intent)
        }

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { searchPois(it) }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchPois(newText)
        }
        return true
    }

    private fun searchPois(searchText: String) {
        val searchQuery = "%$searchText%"
        viewModel.searchPoiList(this, searchQuery)?.observe(this) {
            if (it.isNotEmpty()) {
                updateRecyclerView(it)
            }
        }
    }

}