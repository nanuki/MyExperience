package com.example.myrepository.Repository.Room

import androidx.room.Embedded
import androidx.room.Relation

data class UserwithNoteEntity(
    @Embedded
    var userEntity: UserEntity,
    @Relation(parentColumn = "id", entityColumn = "userEntityid")
    val notelist : MutableList<NoteEntity>

)
