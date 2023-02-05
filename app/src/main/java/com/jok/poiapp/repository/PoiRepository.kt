package com.jok.poiapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.jok.poiapp.database.PoiDatabase
import com.jok.poiapp.mdoel.PlaceOfInterest
import com.jok.poiapp.network.NetworkLayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class PoiRepository {

    companion object {

        private var poiDatabase: PoiDatabase? = null

        private var pois: LiveData<List<PlaceOfInterest>>? = null
        private var filteredPois: LiveData<List<PlaceOfInterest>>? = null

        private fun initializeDB(context: Context) : PoiDatabase {
            return PoiDatabase.getDatabaseClient(context)
        }

        fun insertData(context: Context, pois: List<PlaceOfInterest>) {
            poiDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                poiDatabase!!.poiDao().insertPois(pois)
            }
        }

        fun getPoisFromDb(context: Context) : LiveData<List<PlaceOfInterest>>? {
            poiDatabase = initializeDB(context)
            pois = poiDatabase!!.poiDao().getPois()
            return pois
        }

        fun searchPois(context: Context, searchText: String) : LiveData<List<PlaceOfInterest>>? {
            poiDatabase = initializeDB(context)
            filteredPois = poiDatabase!!.poiDao().searchPois(searchText)
            return filteredPois
        }


    }

    suspend fun getPoiListFromBackend(context: Context): List<PlaceOfInterest>? {

        val request = NetworkLayer.apiClient.getPOIList()

        if (request.failed) {
            return null
        }
        if (!request.isSuccesful) {
            return null
        }

        insertData(context, request.body.poiList)

        return request.body.poiList
    }

}