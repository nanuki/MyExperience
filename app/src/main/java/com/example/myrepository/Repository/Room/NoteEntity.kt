package com.example.myrepository.Repository.Room

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int= 0,
    var note : String,
    var date : String,
    var userEntityid : Int
)
