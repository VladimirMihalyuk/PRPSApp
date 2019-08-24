package com.example.prpsapp.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatabaseDao {

    @Insert
    fun insertClient(night: Client)

    @Query("SELECT * FROM session")
    fun getAllSessions(): LiveData<List<Session>>

    @Query("SELECT *FROM Client WHERE email = :newEmail")
    fun getClientsWithEmail(newEmail: String): List<Client>

    @Query("SELECT idClient FROM Client WHERE email = :newEmail and password = :newPassword")
    fun checkClientExist(newEmail: String, newPassword: String): Long?


    @Query("SELECT T.idTicket, T.specialCode, T.time, T.cinema FROM Ticket as T, TicketsForSession as TFS WHERE TFS.idTicket = T.idTicket and TFS.idSession = :id")
    fun getListOfTickets(id: Long): List<BuyTicketsQuerySecond>?

    @Query("SELECT idClient FROM Client WHERE email = :email")
    fun getIdOfClient(email: String): Long?

    @Insert
    fun insertTFC(tfc: TicketsForClient)

    @Update
    fun updateSession(night: Session)

    @Query("SELECT * FROM Session WHERE idSession = :idSession")
    fun getCurrentSession(idSession: Long):Session?

    @Query("SELECT COUNT() FROM TicketsForClient WHERE idClient = :idClient and idTicket = :idTicket")
    fun getTicketForClientNumber(idClient: Long, idTicket: Long):Long


}