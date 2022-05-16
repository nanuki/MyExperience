package com.example.myrepository.Activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrepository.RecyclerAdaptor.RoomNoteRecyclerAdapter
import com.example.myrepository.Repository.Room.NoteEntity
import com.example.myrepository.ViewModel.RoomNoteViewModel
import com.example.myrepository.databinding.ActivityNoteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*


class RoomNoteActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityNoteBinding

    val viewmodel : RoomNoteViewModel by inject()
    val scope = CoroutineScope(Dispatchers.IO)
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getIntExtra("id", 0)
        val name = intent.getStringExtra("name").toString()
        val age = intent.getStringExtra("age").toString()
        val work  = intent.getStringExtra("work").toString()
        val experience  = intent.getStringExtra("experience").toString()

        binding.name.setText(name)
        binding.age.setText(age)
        binding.work.setText(work)
        binding.experience.setText(experience)

        binding.addNoteButton.setOnClickListener {
            crateNote()

        }
        binding.closebutton.setOnClickListener {
            val intent = Intent(this, RoomUserActivity::class.java)
            startActivity(intent)
        }

        viewmodel.listnote.observe(this, Observer {
            val recycleradapter = RoomNoteRecyclerAdapter(this,it)
            binding.recycler.adapter = recycleradapter
            binding.recycler.layoutManager = LinearLayoutManager(this)
        })

        viewmodel.getlistRecycler(id)

    }




    override fun onBackPressed() {
        val intent = Intent(this,RoomUserActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent)
    }

    fun crateNote(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Note")
        val editText = EditText(this)
        editText.setTextSize(21F)
        editText.setHint("Enter note")
        editText.background = null
        editText.setSingleLine(false)
        editText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION)
        editText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE)
        editText.setLines(3)
        editText.setMaxLines(20)
        editText.setVerticalScrollBarEnabled(true)
        editText.setMovementMethod(ScrollingMovementMethod.getInstance())
        editText.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET)
        builder.setView(editText)
        builder.setPositiveButton("Set", DialogInterface.OnClickListener { dialogInterface, i ->
            val notetext = editText.text.toString()
            println("id :$id")
            val note = NoteEntity(0,note = notetext, date = getDate(), id)
            scope.launch {

                viewmodel.createNote(note)
                delay(100)
                viewmodel.getlistRecycler(id)
            }

        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
        })
        builder.show()

    }

    fun getDate():String{
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return "$day/$month/ $year"
    }




}