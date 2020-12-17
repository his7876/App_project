package com.example.myphamacy

import android.graphics.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


interface PillSearchAPI : RetrofitAPI {
    @GET("getMdcinGrnIdntfcInfoList?")
    fun getPill(@Query("ServiceKey") ServiceKey:String,
                @Query("numOfRows") numOfRows:Int,
                @Query("pageNo") pageNo:Int,
                @Query("item_name") item_name:String


    ): Call<SearchResponseWrapperPill>

}