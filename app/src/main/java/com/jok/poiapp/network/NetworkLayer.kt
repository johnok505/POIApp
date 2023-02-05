package com.jok.poiapp.network

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkLayer {
    private val moshi = Moshi.Builder().addLast(
        com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory()).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://sergiocasero.es/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val poiService: PoiService by lazy {
        retrofit.create(PoiService::class.java)
    }

    val apiClient = ApiClient(poiService)
}