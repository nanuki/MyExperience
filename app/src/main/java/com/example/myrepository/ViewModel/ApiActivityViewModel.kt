package com.example.myrepository.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrepository.Repository.FireBaceRepo.Entnity.Note
import com.example.myrepository.Repository.Repository
import com.example.myrepository.Repository.RetrofitRepo.RetrofitEntity.RegistserandLoginRequest
import com.example.myrepository.Repository.RetrofitRepo.RetrofitEntity.album
import com.example.myrepository.Repository.RetrofitRepo.RetrofitRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ApiActivityViewModel:  ViewModel(), KoinComponent {

    val repo_2 : RetrofitRepository by inject()
    var scope = CoroutineScope(Dispatchers.IO)

    private val _nameliveData = MutableLiveData<MutableList<album>>()
    var nameliveData : MutableLiveData<MutableList<album>> = _nameliveData

    private val _login = MutableLiveData<Boolean>()
    var login : MutableLiveData<Boolean> = _login

    fun getUser(){
        //nameliveData.postValue(repo.createUser())
    }


    suspend fun getNotelist(){
        scope.launch {
            _nameliveData.postValue(repo_2.getNoteList())
        }


    }

}