package com.example.prpsapp.poster

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.prpsapp.database.DatabaseDao
import com.example.prpsapp.database.Session
import kotlinx.coroutines.*

class PosterViewModel(
        val database: DatabaseDao,
        application: Application) : AndroidViewModel(application) {


    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val  sessions: LiveData<List<Session>> = database.getAllSessions()
        //надо бы это как-то пофиксить
//    init {
//        initializeSessions()
//    }

//    private fun initializeSessions(){
//        uiScope.launch {
//            _sessions = getAllSessionsFromDatabase()
//        }
//    }
//
//    private suspend fun getAllSessionsFromDatabase(): LiveData<List<Session>> {
//        return withContext(Dispatchers.IO) {
//            database.getAllSessions()
//        }
//    }


    private val _navigateToBuyTicket = MutableLiveData<Long>()

    val navigateToBuyTicket: LiveData<Long>
        get() = _navigateToBuyTicket

    fun doneNavigating() {
        _navigateToBuyTicket.value = null
    }

    fun onSessionClicked(id: Long){
        _navigateToBuyTicket.value = id
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}