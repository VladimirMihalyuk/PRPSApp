package com.example.prpsapp.buy_tickets

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.prpsapp.database.BuyTicketsQuerySecond
import com.example.prpsapp.database.DatabaseDao
import com.example.prpsapp.database.TicketsForClient
import com.example.prpsapp.database.TicketsForSession
import com.example.prpsapp.prefs
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class BuyTicketsViewModel(val database: DatabaseDao,
                          val idSession: Long, val description: String,
                          val ticketsLeft: Long, val image: String,
                          val filmName: String, application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val tickets = MutableLiveData<Long>()
    val cinema  = MutableLiveData<MutableList<String>>()
    val dates  = MutableLiveData<HashSet<CalendarDay>>()
    val map : HashMap<CalendarDay, MutableList<String>> = hashMapOf()
    val cinemaMap: HashMap<String, Long> = hashMapOf()

    var selectedCinema = ""
    var selectedTickets = 0
    var isSelected = false
    var alreadyBoughtTickets = 0L

    private suspend fun getList(idSession: Long): List<BuyTicketsQuerySecond>?{
        return withContext(Dispatchers.IO){
            async {database.getListOfTickets(idSession)  }.await()
        }
    }


    init {
        uiScope.launch {
            val list = getList(idSession)
            //cinema.value = list?.map{it.cinema}?.toMutableList()
            val dateList = list?.map{it.time}
            dates.value = hashSetOf()
            var i = 0
            if (dateList != null) {
                for(item in dateList){
                    val cal = GregorianCalendar()
                    cal.timeInMillis = item
                    val date = CalendarDay.from(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
                    dates.value?.add(date)
                    if(map[date] == null){
                        map[date] = mutableListOf(list.get(i).cinema)
                    } else {
                        map[date]?.add(list.get(i).cinema)
                    }
                    cinemaMap[list.get(i).cinema] = list.get(i).idTicket
                    ++i
                }
            }
            tickets.value = ticketsLeft
        }

    }

    val buyResultCode = MutableLiveData<Int>()

    private fun insertTicketsDB(tickets: Int, ticketId: Long, _idClient: Long){
        uiScope.launch {
            withContext(Dispatchers.IO) {
                repeat(tickets){
                    database.insertTFC(TicketsForClient(idClient = _idClient, idTicket = ticketId))
                }
                val session = database.getCurrentSession(idSession) ?: return@withContext
                session.ticketsLeft -= tickets
                database.updateSession(session)
            }
        }
    }

    private suspend fun getIdOfClient(email: String): Long? {
        return withContext(Dispatchers.IO){
            async { database.getIdOfClient(email) }.await()
        }
    }

    private suspend fun countTickets(idClient: Long, idTicket: Long){
        withContext(Dispatchers.IO) {
            alreadyBoughtTickets = database.getTicketForClientNumber(idClient,idTicket)
        }
    }

    fun buyTickets(){
        if(isSelected){
            if(prefs.emailClient != null){
                uiScope.launch{
                    val idClient = getIdOfClient(prefs.emailClient.toString()) ?: 0
                    countTickets(idClient,cinemaMap[selectedCinema] ?: 0 )
                    if(selectedTickets + alreadyBoughtTickets > 5){
                        buyResultCode.value = 3
                    } else {
                        insertTicketsDB(selectedTickets, cinemaMap[selectedCinema] ?: 0, idClient)
                        buyResultCode.value = 0
                    }
                }
            } else {
                buyResultCode.value = 2
            }

        } else {
            buyResultCode.value = 1
        }

    }

    val thereIsNoSuchEvent = MutableLiveData<Boolean>()


    fun endThereIsNoSuchEvent(){
        thereIsNoSuchEvent.value = false
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}