package com.example.myrepository.Repository.RetrofitRepo

import android.content.Context
import com.example.myrepository.Repository.FireBaceRepo.Entnity.Note
import com.example.myrepository.Repository.RetrofitRepo.RetrofitEntity.*
import com.example.myrepository.Repository.Repository
import com.example.myrepository.Repository.SharedPrefRepo
import retrofit2.*

class RetrofitRepository(val context: Context): Repository {
    var apiClient = ApiClient(context)
    val sessionManager = SharedPrefRepo(context)
    var name = ""
    override var recycvlerData : MutableList<Note> = mutableListOf()
    override suspend fun createUserNote(note: Note) {
        TODO("Not yet implemented")
    }



    override suspend  fun registerUser(registerandLoginRequest: RegistserandLoginRequest) {
        apiClient.getApiService().registerUser(RegistserandLoginRequest("eve.holt@reqres.in", "pitol"))
            .enqueue(object : Callback<RegisterandLoginResponse>{
                override fun onResponse(
                    call: Call<RegisterandLoginResponse>,
                    response: Response<RegisterandLoginResponse>
                ) {
                    if (response.isSuccessful){
                        sessionManager.saveAuthToken(response.body()?.token!!)
                        println("response : ${response.body()?.token}")
                    }
                }

                override fun onFailure(call: Call<RegisterandLoginResponse>, t: Throwable) {
                    println("response : onFailure")
                }
            })
    }

    override suspend fun loginUser() : Boolean {
        var token = ""
        var login = false
       apiClient.getApiService().loginUser(RegistserandLoginRequest("eve.holt@reqres.in", "pitol"))
           .enqueue(object : Callback<RegisterandLoginResponse>{
               override fun onResponse(
                   call: Call<RegisterandLoginResponse>,
                   response: Response<RegisterandLoginResponse>
               ) {
                   if (response.isSuccessful){
                       token = response.body()?.token!!

                   }

               }

               override fun onFailure(call: Call<RegisterandLoginResponse>, t: Throwable) {
                   TODO("Not yet implemented")
               }
           })
        if (token .equals(sessionManager.fetchAuthToken())){
            login = true
        }
        return login
    }

    override suspend fun createUser() {
        var  user = User(",",",,")
        user = User("job = response.body()?.job!!"," name = response.body()?.name!!")
       apiClient.getApiService().createUser(UserRequest("santexnik","Garik")).enqueue(object : Callback<UserResponse>{
           override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
               if (response.isSuccessful){


               }
           }

           override fun onFailure(call: Call<UserResponse>, t: Throwable) {
               TODO("Not yet implemented")
           }
       })
    }

    override suspend fun getUser(): User {
        var  user = User(",",",,")
        var name = ""
        apiClient.getApiService().getUser().enqueue(object : Callback<UserData.datas>{
            override fun onResponse(
                call: Call<UserData.datas>,
                response: Response<UserData.datas>
            ) {
                if (response.isSuccessful){
                  //  println("name: ${response.body()?.first_name}")
                    name = response.body()?.first_name!!

                }
            }

            override fun onFailure(call: Call<UserData.datas>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
      //  println("name: $name")
        return user
    }

    override suspend fun getUserNote() : MutableList<Note> {
        println("list")
        var list : MutableList<Note> = mutableListOf()
        val call = apiClient.getApiService().getUserList(2).awaitResponse()
         if (call.isSuccessful){
             list = call.body()?.data as MutableList
         }
        println("$list")
        return list
    }

     suspend fun getNoteList() : MutableList<album> {

        var list : MutableList<album> = mutableListOf()
        val call = apiClient.getApiService().getNoteList().awaitResponse()
        if (call.isSuccessful){
            list = call.body() as MutableList<album>
        }
         else{
            println("else")
         }
//         list.add(0, album(1,2,"https://via.placeholder.com/150/24f355",
//             "reprehenderit est deserunt velit ipsam",
//             "https://via.placeholder.com/600/771796"))

        return list
    }





    override fun isregisterUser(): Boolean {
        return sessionManager.isregisterUser()
    }

}