package com.example.myrepository.Repository.Room

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {

    //Work with user data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createUser(userEntity: UserEntity)

    @Query("SELECT * FROM UserEntity WHERE id LIKE:id LIMIT 1")
    fun getUserbyid(id : Int): UserEntity

    @Query("SELECT * FROM UserEntity")
    fun getAllUsers(): MutableList<UserEntity>

    @Query("DELETE FROM UserEntity WHERE id LIKE :id")
    fun deletUserbyid(id: Int)

    //Work with note data

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createNote(noteEntity: NoteEntity)

    @Transaction
    @Query("SELECT * FROM UserEntity WHERE id LIKE:id LIMIT 1")
    fun getNoteyid(id : Int): MutableList<UserwithNoteEntity>

    @Query("DELETE FROM NoteEntity WHERE id LIKE :id")
    fun deleteNote(id: Int)


}