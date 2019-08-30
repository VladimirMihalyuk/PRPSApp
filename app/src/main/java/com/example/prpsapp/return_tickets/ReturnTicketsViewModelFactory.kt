package com.example.prpsapp.return_tickets

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prpsapp.database.DatabaseDao

class ReturnTicketsViewModelFactory(private val dataSource: DatabaseDao,
                                    private val email: String,
                                    private val application: Application): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReturnTicketsViewModel::class.java)) {
            return ReturnTicketsViewModel(dataSource, email, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}