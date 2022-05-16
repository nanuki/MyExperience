package com.example.myrepository.Repository

import com.example.myrepository.Repository.FireBaceRepo.Entnity.Note
import com.example.myrepository.Repository.RetrofitRepo.RetrofitEntity.RegistserandLoginRequest
import com.example.myrepository.Repository.RetrofitRepo.RetrofitEntity.User
import com.example.myrepository.Repository.RetrofitRepo.RetrofitEntity.UserData

interface Repository {
    val recycvlerData : MutableList<Note>

   suspend fun registerUser(registerandLoginRequest: RegistserandLoginRequest)
   suspend fun loginUser() : Boolean
   suspend fun createUser()
   suspend fun getUser():User
   suspend fun createUserNote(note: Note)
   suspend fun getUserNote(): MutableList<Note>
    fun deleteNote(id : String){}

    fun isregisterUser():Boolean{
        return false
    }
}