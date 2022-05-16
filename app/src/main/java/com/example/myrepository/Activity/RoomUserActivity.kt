package com.example.myrepository.Activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrepository.RecyclerAdaptor.RoomUserRecyclerAdapter
import com.example.myrepository.Repository.Room.UserEntity
import com.example.myrepository.ViewModel.RoomUserViewModel
import com.example.myrepository.databinding.ActivityRoomUserBinding
import com.example.myrepository.databinding.RegisterforroomBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoomUserActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.IO)

    val viewModel : RoomUserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRoomUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.additem.setOnClickListener {
            createUser()
        }

        viewModel.listUser.observe(this, Observer {
            val recyclerUserAdaptor = RoomUserRecyclerAdapter(this,it)
            binding.recyclerview.adapter = recyclerUserAdaptor
            binding.recyclerview.layoutManager = LinearLayoutManager(this)
        })

        scope.launch {   viewModel.getUsers() }


    }



    fun createUser(){
        val registerforroomBinding = RegisterforroomBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Fill in the form")
//        val editText = EditText(this)
//        editText.setHint("Enter Name")
//        editText.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(registerforroomBinding.root)

        builder.setPositiveButton("Set", DialogInterface.OnClickListener { dialogInterface, i ->
            val name = registerforroomBinding.editname.text.toString()
            val age = registerforroomBinding.editage.text.toString()
            val work = registerforroomBinding.editwork.text.toString()
            val experience = registerforroomBinding.experience.text.toString()
            val user = UserEntity(name =  name, age = age,work = work, experience = experience)
            scope.launch {
                viewModel.createUser(user)
                delay(100)
                viewModel.getUsers()

            }

        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->

        })
        builder.show()

    }

    override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent)
    }
}