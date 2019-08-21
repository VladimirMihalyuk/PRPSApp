package com.example.prpsapp.buy_tickets

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.prpsapp.database.DatabaseDao
import kotlinx.coroutines.*

class BuyTicketsViewModel(val database: DatabaseDao,
                          val idSession: Long, val description: String,
                          val ticketsLeft: Long, val image: String,
                          val filmName: String, application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    init {
        uiScope.launch {
            getListOfTickets(idSession)
        }
    }



    private suspend fun getListOfTickets(id: Long){
        withContext(Dispatchers.IO){
            val list = database.getListOfTickets(id)
            Log.d("WTF", "${list}")
        }
    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}