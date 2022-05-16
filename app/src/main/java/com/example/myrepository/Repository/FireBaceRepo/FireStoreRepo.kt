package com.example.myrepository.Repository.FireBaceRepo

import android.content.Context
import com.example.myrepository.Repository.FireBaceRepo.Entnity.Note
import com.example.myrepository.Repository.Repository
import com.example.myrepository.Repository.RetrofitRepo.RetrofitEntity.RegistserandLoginRequest
import com.example.myrepository.Repository.RetrofitRepo.RetrofitEntity.User
import com.example.myrepository.Repository.SharedPrefRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class FireStoreRepo(val context: Context) : Repository {
    var firebaseAuth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()
    var sessionManager = SharedPrefRepo(context)

    override var recycvlerData: MutableList<Note> = mutableListOf()

    override suspend fun registerUser(registerandLoginRequest: RegistserandLoginRequest) {
        firebaseAuth.createUserWithEmailAndPassword(registerandLoginRequest.email,registerandLoginRequest.password).await()
        val token : String? = firebaseAuth.uid
        token?.let {  sessionManager.saveAuthToken(token) }

    }

    override suspend fun loginUser(): Boolean {
        var signin = false
        val token : String? = sessionManager.fetchAuthToken()
        token?.let { firebaseAuth.signInWithCustomToken(it)}
        val isSuccessful = firebaseAuth.currentUser?.getIdToken(false)?.isSuccessful
        if (isSuccessful == true) {
            signin = true
        }
        return signin
    }

    override fun isregisterUser(): Boolean {
        return sessionManager.isregisterUser()
    }


     override suspend fun createUser() {
         val token : String? = sessionManager.fetchAuthToken()
         token?.let {
             val user = User("worker",it)
             firestore.collection("user").document(it).set(user).await() }

    }

    override suspend fun getUser(): User {
        TODO("Not yet implemented")
    }

    override suspend fun createUserNote(note: Note) {
        val token : String? = sessionManager.fetchAuthToken()
        val notecolecion = firestore.collection("user")
            .document(token!!)
            .collection("note")
            .document()
        val id = notecolecion.id
        note.id = id
        notecolecion.set(note).await()

    }

    override suspend fun getUserNote(): MutableList<Note> {
        val list : MutableList<Note> = mutableListOf()
        val token : String? = sessionManager.fetchAuthToken()
        val colection = firestore.collection("user").document(token!!).collection("note")
        val documents = colection.get().await()
        for (document in documents){
            val doctoobject = document.toObject<Note>()
            list.add(doctoobject)
        }
        recycvlerData = list

        return list
    }

    override fun deleteNote(id : String) {
        val token : String? = sessionManager.fetchAuthToken()
       firestore
           .collection("user").document(token!!)
           .collection("note").document(id).delete()
    }



}