package com.example.myphamacy

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface RetrofitAPI{  // pharmacy data를 위한 retrofitAPI
    @GET("getParmacyLcinfoInqire?")
    fun getPharmacy(@Query("ServiceKey") ServiceKey:String,
                    @Query("WGS84_LON") WGS84_LON:Double,
                    @Query("WGS84_LAT") WGS84_LAT:Double)
            : Call<SearchResponseWrapper>

}


