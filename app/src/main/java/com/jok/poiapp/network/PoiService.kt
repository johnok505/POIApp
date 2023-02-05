package com.jok.poiapp.network

import com.jok.poiapp.mdoel.PoiListResponse
import retrofit2.Response
import retrofit2.http.GET

interface PoiService {

    @GET("pois.json")
    suspend fun getPOIList(): Response<PoiListResponse>

}