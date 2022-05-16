package com.example.myrepository.Repository.Room

import android.content.Context
import com.google.android.gms.actions.NoteIntents

class RoomRepository(val context: Context) {

    fun createUser(userEntity: UserEntity){
        DB.getInstance(context).getDao().createUser(userEntity)
    }

     fun getAllUsers():MutableList<UserEntity>{
        return DB.getInstance(context).getDao().getAllUsers()
    }

     fun deleteUserByid(id: Int){
        DB.getInstance(context).getDao().deletUserbyid(id)
    }

     fun createNoteforUser(noteEntity: NoteEntity){
        DB.getInstance(context).getDao().createNote(noteEntity)
    }

     fun getuserNotes(id:Int): MutableList<NoteEntity>{
        return DB.getInstance(context).getDao().getNoteyid(id)[0].notelist

    }

     fun deletNote(id: Int){
        DB.getInstance(context).getDao().deleteNote(id)
    }

}