package com.example.prpsapp.buy_tickets

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.prpsapp.database.Session

@BindingAdapter("filmImage")
fun ImageView.setFilmImage(item: BuyTicketsViewModel?) {
    item?.image?.let {
        Log.d("WTF", "${item?.image}")
        val resID = context.resources.getIdentifier(item.image, "drawable", context.packageName)
        setImageResource(resID)
    }
}