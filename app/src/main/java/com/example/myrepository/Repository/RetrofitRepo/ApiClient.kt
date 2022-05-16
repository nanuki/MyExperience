package com.example.myrepository.Repository.RetrofitRepo

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownServiceException

class ApiClient(val context: Context) {
    private lateinit var apiService : UserApi

    // Initialize ApiService if not initialized yet
     fun getApiService() : UserApi{
        if (!:: apiService.isInitialized){
            val retrofit = Retrofit.Builder()
              //  .client(okkHttpClient()) // Add our Okhttp client
                .baseUrl(Constans.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()

            apiService = retrofit.create(UserApi::class.java)
        }
        return apiService

    }

     fun okkHttpClient(): OkHttpClient{

        return OkHttpClient.Builder().addInterceptor(AuthInterceptor(context)).build()

    }
}