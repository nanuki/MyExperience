package com.example.myrepository.Activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrepository.RecyclerAdaptor.FireNoteRecyclerAdapter
import com.example.myrepository.Repository.FireBaceRepo.Entnity.Note
import com.example.myrepository.ViewModel.FireStoreNoteViewModel
import com.example.myrepository.databinding.ActivityHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class FireStoreNoteActivity : AppCompatActivity(), KoinComponent{
    val viewModel :FireStoreNoteViewModel by viewModel()
    val scope = CoroutineScope(Dispatchers.IO)

    var note:Note? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.additem.setOnClickListener {
            createUserNote()

        }


        viewModel.nameliveData.observe(this, Observer {
            val recyclerNoteAdaptor = FireNoteRecyclerAdapter(this,it)
            val recyclerView = binding.recyclerview
            recyclerView.adapter = recyclerNoteAdaptor
            recyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)
        })

        scope.launch {
            viewModel.getUserNote()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent)
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




    private fun createUserNote() {
        note = makenoteEntity("https://via.placeholder.com/200/61a65","Green")

        var selectedFruits: String
        var selectedFruitsIndex: Int = 0
        val fruits = arrayOf("Green", "Yellow", "Sky-blue", "Purple", "Dark blue")

        selectedFruits = fruits[selectedFruitsIndex]
        MaterialAlertDialogBuilder(this)
            .setTitle("Select color")
            .setSingleChoiceItems(fruits, selectedFruitsIndex) { dialog_, which ->
                selectedFruitsIndex = which
                selectedFruits = fruits[which]

               when( selectedFruitsIndex ){
                   0-> note = makenoteEntity("https://via.placeholder.com/200/61a65","Green")
                   1-> note = makenoteEntity("https://via.placeholder.com/200/fdf73e","Yellow")
                   2-> note = makenoteEntity("https://via.placeholder.com/200/56acb2","Sky-blue")
                   3-> note = makenoteEntity("https://via.placeholder.com/200/8f209a","Purple")
                   4-> note = makenoteEntity("https://via.placeholder.com/200/501fe1","Dark blue")

               }




            }
            .setPositiveButton("Ok") { dialog, which ->
                                scope.launch {
                    scope.launch {
                        note?.let { viewModel.createUserNote(it) }
                        viewModel.getUserNote()

                    }

                }

            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    fun makenoteEntity(path :String, description :String): Note{
        return Note("",description, path)
    }
}
