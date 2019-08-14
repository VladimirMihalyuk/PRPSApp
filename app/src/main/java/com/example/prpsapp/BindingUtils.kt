package com.example.prpsapp

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.prpsapp.database.Session




@BindingAdapter("nameOfFilm")
fun TextView.setNameOfFilm(item: Session?) {
    item?.let {
        text = item.nameOfFilm
    }
}


@BindingAdapter("durationOfFilm")
fun TextView.setDurationOfFilm(item: Session?) {
    item?.let {
        text = "Duration of film ${item.duration} minutes"
    }
}

@BindingAdapter("tiketsLeft")
fun TextView.setTicketsLeft(item: Session?) {
    item?.let {
        text = "Tickets left: ${item.ticketsLeft}"
    }
}

@BindingAdapter("filmImage")
fun ImageView.setFilmImage(item: Session?) {
    item?.let {
        val resID = context.resources.getIdentifier(item.image, "drawable", context.packageName)
        setImageResource(resID)
    }
}
