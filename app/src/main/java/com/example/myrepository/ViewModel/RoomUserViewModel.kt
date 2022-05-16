package com.example.myrepository.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrepository.Repository.Repository
import com.example.myrepository.Repository.Room.NoteEntity
import com.example.myrepository.Repository.Room.RoomRepository

import com.example.myrepository.Repository.Room.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RoomUserViewModel : ViewModel(), KoinComponent{
    val repo : RoomRepository by inject()

    private val _listUser = MutableLiveData<MutableList<UserEntity>>()
    var listUser : MutableLiveData<MutableList<UserEntity>> = _listUser

     fun createUser(userEntity: UserEntity){
        viewModelScope.launch(Dispatchers.IO){
            repo.createUser(userEntity)
        }
    }
     fun getUsers(){
        viewModelScope.launch(Dispatchers.IO){
            _listUser.postValue(repo.getAllUsers())
        }
    }

     fun deleteUser(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteUserByid(id)
        }

    }


}