package com.example.myrepository.Repository.RetrofitRepo.RetrofitEntity

class UserData {
    var data : datas? = null
    var photos : albums? = null

    data class datas(
        val avatar: String,
        val email: String,
        val first_name: String,
        val id: Int,
        val last_name: String
    )

    data class albums(
        val albumId: Int,
        val id: Int,
        val thumbnailUrl: String,
        val title: String,
        val url: String
    )

}