package com.example.myrepository.Repository

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.example.myrepository.R

class SharedPrefRepo (val context: Context) {
     var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, "")
    }


    fun isregisterUser():Boolean{
        var chack  = false
        if (fetchAuthToken()?.isNotEmpty()!!){
            chack = true
        }
        return  chack
    }
}