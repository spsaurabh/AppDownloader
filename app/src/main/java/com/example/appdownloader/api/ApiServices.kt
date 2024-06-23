package com.example.appdownloader.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiServices {
    //common interface function for all get api's
    @GET
    fun hitGetApiWithoutJsonParams(@Url url:String): Call<JsonObject>
}