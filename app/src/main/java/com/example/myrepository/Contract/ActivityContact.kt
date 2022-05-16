package com.example.myrepository.Contract

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.myrepository.Activity.MainActivity
import com.example.myrepository.Activity.RoomNoteActivity

class ActivityContact : ActivityResultContract<Int, AdddataForContract<Int, String>>() {
    var intent : Intent? = null
    override fun createIntent(context: Context, input: Int?): Intent {
        if(input == 5){
            intent = Intent(context, RoomNoteActivity::class.java)
            intent!!.putExtra("lunch_input", input)
        }

        else if(input == 6){
            intent = Intent(context, MainActivity::class.java)
            intent!!.putExtra("lunch_input", input)
        }



        return intent!!
    }

    override fun parseResult(resultCode: Int, intent: Intent?): AdddataForContract<Int, String> {
        return AdddataForContract(0,"Garik")
    }

}