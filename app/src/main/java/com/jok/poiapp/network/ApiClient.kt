package com.jok.poiapp.network

import com.jok.poiapp.mdoel.PoiListResponse
import retrofit2.Response
import java.lang.Exception

class ApiClient(
    private val poiService: PoiService
) {
    suspend fun getPOIList(): SimpleResponse<PoiListResponse> {
        return simpleApiCall {
            poiService.getPOIList()
        }
    }

    private inline fun <T> simpleApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }



}