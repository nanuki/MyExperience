package com.example.myrepository.RecyclerAdaptor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrepository.Activity.RoomNoteActivity
import com.example.myrepository.R
import com.example.myrepository.Repository.Room.UserEntity
import com.example.myrepository.ViewModel.RoomUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RoomUserRecyclerAdapter(val context: Context, var data : MutableList<UserEntity>)
    : RecyclerView.Adapter<RoomUserRecyclerAdapter.CustomHolder>(), KoinComponent {

    val viewmodel : RoomUserViewModel by inject()
    val scope = CoroutineScope(Dispatchers.IO)


    class CustomHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var name : TextView
        var delete : ImageView
        var add : ImageView
        init {
             name= itemView.findViewById(R.id.name)
             delete= itemView.findViewById(R.id.delete)
             add= itemView.findViewById(R.id.addusernote)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_row,parent,false)
        return CustomHolder(view)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val currentdata = data[getindexbyposition(position)]
        holder.name.setText(currentdata.name).toString()

        holder.add.setOnClickListener {
            val intent = Intent(context, RoomNoteActivity::class.java)
            intent.putExtra("id",currentdata.id)
            intent.putExtra("name",currentdata.name)
            intent.putExtra("age",currentdata.age)
            intent.putExtra("work",currentdata.work)
            intent.putExtra("experience",currentdata.experience)
            context.startActivity(intent)
        }
        holder.delete.setOnClickListener {
            scope.launch {
                viewmodel.deleteUser(currentdata.id)
            }

            removerow(getindexbyposition(position))

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun getindexbyposition(position: Int):Int{
        return position % data.size

    }

    @SuppressLint("NotifyDataSetChanged")
    fun removerow(id : Int){
        data.removeAt(id)
        notifyDataSetChanged()
    }
}