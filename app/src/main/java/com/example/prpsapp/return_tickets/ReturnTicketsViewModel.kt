package com.example.prpsapp.return_tickets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.prpsapp.database.DatabaseDao
import com.example.prpsapp.database.Session
import kotlinx.coroutines.*

class ReturnTicketsViewModel(val database: DatabaseDao,
                             val email: String,
                             application: Application): AndroidViewModel(application){
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val tickets = database.getTicketsForClient(email)

    private val _deleteId = MutableLiveData<Long?>()
    val deleteId: LiveData<Long?>
        get() = _deleteId

    fun returnTickets(id: Long){
        _deleteId.value = id
    }

    fun deleteTicketsFromDB(idTicket: Long, email: String){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val idClient = database.getIdOfClient(email)
                val ticketsNumber = database.countTicketsForClient(idTicket, idClient?: 0)
                val session = database.getSessionByEmailAndTicket(idClient?: 0, idTicket)
                session?.let {
                    session.ticketsLeft += ticketsNumber ?: 0
                    database.updateSession(session) }
                database.deleteFromTFC(idClient?: 0, idTicket)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}