package com.jok.poiapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jok.poiapp.mdoel.PlaceOfInterest

@Dao
interface PoiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPois(pois: List<PlaceOfInterest>)

    @Query("SELECT * from pois")
    fun getPois(): LiveData<List<PlaceOfInterest>>

    @Query("SELECT * from pois WHERE title LIKE :searchText")
    fun searchPois(searchText: String): LiveData<List<PlaceOfInterest>>

}