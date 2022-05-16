package com.example.myrepository.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrepository.RecyclerAdaptor.ApiRecyclerAdapter
import com.example.myrepository.ViewModel.ApiActivityViewModel
import com.example.myrepository.databinding.ActivityApiBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ApiActivity : AppCompatActivity() {

    val viewModel : ApiActivityViewModel by inject()
    val scope = CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityApiBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.nameliveData.observe(this, Observer {
            val recyclerNoteAdaptor = ApiRecyclerAdapter(this,it)
            val recyclerView = binding.recyclerview
            recyclerView.adapter = recyclerNoteAdaptor
            recyclerView.layoutManager = LinearLayoutManager(this)
        })

        scope.launch {
            viewModel.getNotelist()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent)
    }


}