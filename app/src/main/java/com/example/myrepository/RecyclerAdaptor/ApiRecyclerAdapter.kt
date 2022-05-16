package com.example.myrepository.RecyclerAdaptor

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrepository.R
import com.example.myrepository.Repository.RetrofitRepo.RetrofitEntity.album
import com.example.myrepository.ViewModel.FireStoreNoteViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ApiRecyclerAdapter(val context : Context, val  data : MutableList<album>)
    : RecyclerView.Adapter<ApiRecyclerAdapter.CustomHelder>(), KoinComponent {
    val viewModel : FireStoreNoteViewModel by inject<FireStoreNoteViewModel>()
    val scope = CoroutineScope(Dispatchers.IO)

    class CustomHelder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title : TextView
        var id : TextView
        var image : ImageView

        init {
            title = itemView.findViewById(R.id.title)
            id = itemView.findViewById(R.id.id)
            image = itemView.findViewById(R.id.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHelder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_retrofit,parent,false)
        return CustomHelder(view)
    }

    override fun onBindViewHolder(holder: CustomHelder, position: Int) {
        val currentdata = data[getindexbyposition(position)]
        holder.title.setText(currentdata.title)
        holder.id.setText(currentdata.id.toString())
        showimage(holder.image,currentdata.url)
        //5e3a73

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

    fun showimage(imageView: ImageView, path: String){
        Picasso.get().load(path)
//            .placeholder(R.drawable.ic_launcher_background)
            .error(R.mipmap.ic_launcher)
            .into(imageView)
    }
}