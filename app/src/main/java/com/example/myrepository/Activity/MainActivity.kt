package com.example.myrepository.Activity

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myrepository.ViewModel.FireStoreNoteViewModel
import com.example.myrepository.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent


class MainActivity : AppCompatActivity(), KoinComponent {

    val viewModel : FireStoreNoteViewModel by viewModel()

    val scope = CoroutineScope(Dispatchers.IO)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.buttonFireStore.setOnClickListener {
            if (checkNetworkConnection(this)){
                if (viewModel.isregisterUser()){
                    scope.launch { login() }
                }
                else {
                    val intent = Intent(this , FireStorLoginActivity::class.java)
                    startActivity(intent)
                }

            }
            else{
                Toast.makeText(this, "Connect to the internet", Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttonRoom.setOnClickListener {
            val intent_ = Intent(this , RoomUserActivity::class.java)
            startActivity(intent_)
        }
        binding.RestAPI.setOnClickListener {
            if (checkNetworkConnection(this)){
                val intent_ = Intent(this , ApiActivity::class.java)
                startActivity(intent_)
            }
            else{
                Toast.makeText(this, "Connect to the internet", Toast.LENGTH_SHORT).show()
            }

        }


    }
    suspend fun login() {
        withContext(Dispatchers.Main){
            viewModel.login.observe(this@MainActivity, Observer {
                if (it){
                    val intent = Intent(this@MainActivity, FireStoreNoteActivity::class.java)
                    startActivity(intent)
                }
            })
        }
        viewModel.login()


    }







    fun checkNetworkConnection(context: MainActivity): Boolean {
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

    override fun onBackPressed() {
        if (getIntent().getExtras() != null && getIntent().getExtras()?.getBoolean("EXIT", false) ?: true) {
            finish();
        }
    }



    }