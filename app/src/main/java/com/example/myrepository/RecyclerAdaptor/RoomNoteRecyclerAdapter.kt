package com.example.myrepository.RecyclerAdaptor

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.InputType
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrepository.R
import com.example.myrepository.Repository.Room.NoteEntity
import com.example.myrepository.ViewModel.RoomNoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class RoomNoteRecyclerAdapter(val context : Context, val  data : MutableList<NoteEntity>)
    : RecyclerView.Adapter<RoomNoteRecyclerAdapter.CustomHelder>(), KoinComponent {
    val viewModel : RoomNoteViewModel by inject()
    val scope = CoroutineScope(Dispatchers.IO)

    class CustomHelder(itemView: View): RecyclerView.ViewHolder(itemView){
        var button : Button
        var position : TextView
        var delete : ImageView

        init {
            button = itemView.findViewById(R.id.date)
            position = itemView.findViewById(R.id.position)
            delete  = itemView.findViewById(R.id.delete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHelder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_row,parent,false)
        return CustomHelder(view)
    }

    override fun onBindViewHolder(holder: CustomHelder, position: Int) {
        val currentdata = data[getindexbyposition(position)]
        val pos = position+1
        holder.position.setText(pos.toString())

        holder.button.setOnClickListener {
            showText(currentdata.note)
        }


        holder.delete.setOnClickListener {
            scope.launch {
                viewModel.deletnote(currentdata.id)
            }
            deleteItem(getindexbyposition(position))
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
    fun getindexbyposition(position : Int): Int{
        return position % data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(position: Int){
        data.removeAt(position)
        notifyDataSetChanged()
    }

    fun showText(text:String){
        val builder = AlertDialog.Builder(context)
        val textview = TextView(context)
//        val params: LinearLayout.LayoutParams =
//            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT)
//        params.setMargins(10, 100, 10, 10)
//        textview.setLayoutParams(params)
        textview.background = null
        textview.setText(text)
        textview.setTextSize(21F)
        textview.setSingleLine(false)
        textview.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION)
        textview.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE)
   //     textview.setPadding(2,2,2,2)
//        textview.setLines(3)
//        textview.setMaxLines(20)
        textview.setVerticalScrollBarEnabled(true)
        textview.setMovementMethod(ScrollingMovementMethod.getInstance())
        textview.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET)
        builder.setView(textview)
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
        })
        builder.show()
    }

}