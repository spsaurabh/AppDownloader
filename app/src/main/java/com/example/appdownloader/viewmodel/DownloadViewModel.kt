package com.example.appdownloader.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appdownloader.api.ApiClient
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DownloadViewModel:ViewModel() {
    var apiObj= MutableLiveData<JsonObject>()
    var isApiFailure= MutableLiveData<Boolean>()
    fun hitHomePageApi(apiEndpoint:String){
        val apiConfig = ApiClient.getApiService()?.hitGetApiWithoutJsonParams(apiEndpoint)
        apiConfig?.enqueue(object : Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful){
                    apiObj.value=response.body()
                    Log.e("DownloadViewModel","${response.body()}")
                }else{
                    isApiFailure.value=true
                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                isApiFailure.value=true
            }
        })
    }
}