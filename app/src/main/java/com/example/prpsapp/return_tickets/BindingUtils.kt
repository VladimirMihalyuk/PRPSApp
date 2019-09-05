package com.example.prpsapp.return_tickets

import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.prpsapp.R
import com.example.prpsapp.database.ReturnTicketsQueryFirst
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("filmImage")
fun ImageView.setFilmImage(item: ReturnTicketsQueryFirst?) {
    item?.let {
        val resID = context.resources.getIdentifier(item.img, "drawable", context.packageName)
        setImageResource(resID)
    }
}

@BindingAdapter("date")
fun TextView.setDate(item: ReturnTicketsQueryFirst?) {
    item?.let {
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        var date = formatter.format(Date(item.time))
        text = "Date: $date"
    }
}

@BindingAdapter("tickets")
fun TextView.setTickets(item: ReturnTicketsQueryFirst?) {
    item?.let {
        text = "You bought ${item.tickets} ${if(item.tickets == 1L) "ticket" else "tickets"}"
    }
}

@BindingAdapter("blink")
fun ImageView.setAnimation(flag: Boolean?) {
    flag?.let {
         if(flag){
             Log.d("WTF", "ANIMATION")
             val animation = AnimationUtils.loadAnimation(context, R.anim.blink_animation)
             this.startAnimation(animation)
         }
    }
}
