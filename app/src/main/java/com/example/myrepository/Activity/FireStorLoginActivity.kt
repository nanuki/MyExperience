package com.example.myrepository.Activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.myrepository.R
import com.example.myrepository.Repository.RetrofitRepo.RetrofitEntity.RegistserandLoginRequest
import com.example.myrepository.ViewModel.FireStoreNoteViewModel
import com.example.myrepository.databinding.RegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class FireStorLoginActivity : AppCompatActivity() {

    val viewModel : FireStoreNoteViewModel by viewModel()

    val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fire_stor_login)

        if (checkNetworkConnection(this)){
            asktoRegister()
        }
        else{
            Toast.makeText(this, "Turn on internet ", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent)
    }






    fun asktoRegister(){
        val builder = AlertDialog.Builder(this)
        val registerBinding = RegisterBinding.inflate(layoutInflater)
        builder.setView(registerBinding.root)
        builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
            val email = registerBinding.editTextTextEmailAddress.text.toString()
            val password = registerBinding.editTextTextPassword.text.toString()
            if (email.isEmpty()){
                Toast.makeText(this@FireStorLoginActivity,"email", Toast.LENGTH_SHORT).show()
                asktoRegister()
            }
            else if (password.isEmpty()){
                Toast.makeText(this@FireStorLoginActivity,"password", Toast.LENGTH_SHORT).show()
                asktoRegister()
            }

            else{
                val registerandLoginRequestequest = RegistserandLoginRequest(email = email, password = password)
                scope.launch {
                    viewModel.registerUser(registerandLoginRequestequest)
                    withContext(Dispatchers.IO){
                        // delay(5000)
                        login()
                    }
                }
            }

        })
        builder.setNegativeButton("Cansel", DialogInterface.OnClickListener { dialogInterface, i ->
            onBackPressed()
            Toast.makeText(this@FireStorLoginActivity,"cansel", Toast.LENGTH_SHORT).show()

        })
        builder.show()
        //scope.launch { viewModel.login() }


    }




    fun checkNetworkConnection(context: Context): Boolean {
        val isonline = true
        val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val currentNetwork: Network = connectivityManager.activeNetwork?:return false
        }
        else {

            @Suppress("DEPRECATION")
            val currentNetwork = connectivityManager.activeNetworkInfo ?: return false

        }
        return isonline
    }

    suspend fun login() {
        withContext(Dispatchers.Main){
            viewModel.login.observe(this@FireStorLoginActivity, Observer {
                if (it){
                    val intent = Intent(this@FireStorLoginActivity, FireStoreNoteActivity::class.java)
                    startActivity(intent)
                }
            })
        }
        viewModel.login()


    }
}