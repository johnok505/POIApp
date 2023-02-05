package com.jok.poiapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.jok.poiapp.repository.PoiRepository
import com.jok.poiapp.mdoel.PlaceOfInterest
import kotlinx.coroutines.launch

class PoiViewModel(application: Application): AndroidViewModel(application) {
    private val repository = PoiRepository()
    private val _poiListLiveData = MutableLiveData<List<PlaceOfInterest>?>()
    val poiLiveData: LiveData<List<PlaceOfInterest>?> = _poiListLiveData
    private var databasePoisLiveData: LiveData<List<PlaceOfInterest>>? = null
    private var filteredPoisLiveData: LiveData<List<PlaceOfInterest>>? = null

    //get all POIs from DB
    fun getPoiList(context: Context): LiveData<List<PlaceOfInterest>>? {
        databasePoisLiveData = PoiRepository.getPoisFromDb(context)
        return databasePoisLiveData
    }

    //fetch pois from back end
    fun fetchPoiList(context: Context) {
        viewModelScope.launch {
            val response = repository.getPoiListFromBackend(context)
            _poiListLiveData.postValue(response)
        }
    }

    //search database for POIs
    fun searchPoiList(context: Context, searchText: String): LiveData<List<PlaceOfInterest>>? {
        filteredPoisLiveData = PoiRepository.searchPois(context, searchText)
        return filteredPoisLiveData
    }

}