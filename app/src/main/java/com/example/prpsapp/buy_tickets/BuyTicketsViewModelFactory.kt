package com.example.prpsapp.buy_tickets

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prpsapp.database.DatabaseDao


class BuyTicketsViewModelFactory(private val dataSource: DatabaseDao,
                                 private val idSession: Long,private val description: String,
                                 private val ticketsLeft: Long,private val image: String,
                                 private val filmName: String, private val application: Application): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BuyTicketsViewModel::class.java)) {
            return BuyTicketsViewModel(dataSource, idSession, description, ticketsLeft,image, filmName, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}