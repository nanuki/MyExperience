package com.example.myrepository.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrepository.Repository.FireBaceRepo.Entnity.Note
import com.example.myrepository.Repository.Repository
import com.example.myrepository.Repository.RetrofitRepo.RetrofitEntity.RegistserandLoginRequest
import com.example.myrepository.Repository.RetrofitRepo.RetrofitRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FireStoreNoteViewModel : ViewModel(), KoinComponent {
    val repo : Repository by inject()
    val repo_2 : RetrofitRepository by inject()
    var scope = CoroutineScope(Dispatchers.IO)

    private val _nameliveData = MutableLiveData<MutableList<Note>>()
    var nameliveData :MutableLiveData<MutableList<Note>> = _nameliveData

    private val _login = MutableLiveData<Boolean>()
    var login : MutableLiveData<Boolean> = _login

    fun getUser(){
        //nameliveData.postValue(repo.createUser())
    }

    suspend fun registerUser(registerandLoginRequest: RegistserandLoginRequest){
       repo.registerUser(registerandLoginRequest)

   }
    suspend fun login() {
        return  _login.postValue( repo.loginUser())
    }

    suspend fun createUserNote(note: Note) {
        repo.createUserNote(note)
    }

   suspend fun getUserNote(){
       scope.launch {
           _nameliveData.postValue(repo.getUserNote())
       }
   }
    

    suspend fun deleteNote(id : String){
        repo.deleteNote(id)
    }


    fun isregisterUser(): Boolean{
        return repo.isregisterUser()
    }
}