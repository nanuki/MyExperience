package com.example.myrepository.Repository.RetrofitRepo

import android.content.Context
import com.example.myrepository.Repository.SharedPrefRepo
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor (val context: Context) : Interceptor{
    private var sessionManager = SharedPrefRepo(context)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestbuilder = chain.request().newBuilder()
        sessionManager.fetchAuthToken()?.let { requestbuilder.addHeader("authorization", it) }


        return chain.proceed(requestbuilder.build())
    }

}