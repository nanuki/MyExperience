package com.example.myrepository.Repository.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class,NoteEntity::class], version = 1)
abstract class DB : RoomDatabase() {

    abstract fun getDao(): Dao

    companion object{
        private var INSTANCE : DB? = null
        fun getInstance(context: Context):DB{
            return INSTANCE?:Room.databaseBuilder(context.applicationContext,DB::class.java,"db").build()
        }

    }
}