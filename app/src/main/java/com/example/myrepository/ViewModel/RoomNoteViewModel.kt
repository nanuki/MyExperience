package com.example.myrepository.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrepository.Repository.Room.NoteEntity
import com.example.myrepository.Repository.Room.RoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RoomNoteViewModel: ViewModel(), KoinComponent {
    val repo : RoomRepository by inject()


    private val _listnote = MutableLiveData<MutableList<NoteEntity>>()
    var listnote : MutableLiveData<MutableList<NoteEntity>> = _listnote

    fun createNote(noteEntity: NoteEntity){
        viewModelScope.launch (Dispatchers.IO)
        {
            repo.createNoteforUser(noteEntity)
        }

    }

    fun getlistRecycler(id:Int){
        viewModelScope.launch (Dispatchers.IO){
            _listnote.postValue(repo.getuserNotes(id))
        }
    }

    fun deletnote(id: Int){
        viewModelScope.launch (Dispatchers.IO) {
            repo.deletNote(id)
        }

    }


}