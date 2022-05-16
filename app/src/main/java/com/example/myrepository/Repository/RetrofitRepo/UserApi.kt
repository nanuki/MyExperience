package com.example.myrepository.Repository.RetrofitRepo

import com.example.myrepository.Repository.RetrofitRepo.RetrofitEntity.*
import com.google.firebase.firestore.auth.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @POST(Constans.REGISTER_URL)
     fun registerUser(@Body request: RegistserandLoginRequest): Call<RegisterandLoginResponse>

    @POST(Constans.LOGIN_URL)
     fun loginUser(request: RegistserandLoginRequest) : Call<RegisterandLoginResponse>


     @POST(Constans.CREATE_USERS_URL)
     fun createUser(@Body userrequset : UserRequest) : Call<UserResponse>

     @GET(Constans.USER_URL)
     fun getUser(): Call<UserData.datas>

     @GET("/api/users")
     fun getUserList(@Query("page") page : Int): Call<UsersList>

    @GET("photos")
    fun getNoteList(): Call<List<album>>


    suspend fun deleteUser()
}

object UserRetrofit {
    var retrofit = Retrofit.Builder()
        .baseUrl("https://reqres.in/")
//        .client(OkHttpClient.Builder().addInterceptor(object :Interceptor{
//            override fun intercept(chain: Interceptor.Chain): Response {
//                val request = chain.request()
//                val newrequest = request.newBuilder().addHeader("Apikey","hjklut25tyj,4jfdxkl").build()
//                return chain.proceed(newrequest)
//            }
//        }).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
