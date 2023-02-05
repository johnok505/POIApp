package com.jok.poiapp.mdoel

import com.squareup.moshi.Json

data class PoiListResponse(
    @Json(name = "list")
    val poiList: List<PlaceOfInterest> = listOf()
)