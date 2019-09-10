package com.example.prpsapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatabaseDao {

    @Insert
    fun insertClient(night: Client)

    @Query("SELECT * FROM session WHERE ticketsLeft > 0")
    fun getAllSessions(): LiveData<List<Session>?>

    @Query("SELECT *FROM Client WHERE email = :newEmail")
    fun getClientsWithEmail(newEmail: String): List<Client>

    @Query("SELECT idClient FROM Client WHERE email = :newEmail and password = :newPassword")
    fun checkClientExist(newEmail: String, newPassword: String): Long?


    @Query("SELECT T.idTicket, T.specialCode, T.time, T.cinema FROM Ticket as T, TicketsForSession as TFS WHERE TFS.idTicket = T.idTicket and TFS.idSession = :id")
    fun getListOfTickets(id: Long): List<BuyTicketsQueryFirst>?

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

    @Query("SELECT T.idTicket, T.time, T.cinema, S.nameOfFilm, S.image, Count() as tickets FROM TicketsForClient as TFC, Ticket as T,  TicketsForSession as TFS, Session as S, Client as C WHERE C.email = :email and C.idClient = TFC.idClient and TFC.idTicket = T.idTicket and T.idTicket = TFS.idTicket and TFS.idSession = S.idSession GROUP BY TFC.idTicket ORDER BY T.time")
    fun getTicketsForClient(email: String): LiveData<List<ReturnTicketsQueryFirst>>

    @Query("SELECT Count() FROM TicketsForClient WHERE idTicket = :idTicket and idClient = :idClient")
    fun countTicketsForClient(idTicket: Long, idClient: Long): Long?

    @Query("SELECT S.idSession, S.description, S.duration, S.ticketsLeft, S.image, S.nameOfFilm  FROM Session as S, TicketsForClient as TFC, Ticket as T, TicketsForSession as TFS WHERE TFC.idTicket = :idTicket and TFC.idClient = :idClient and TFC.idTicket = T.idTicket and T.idTicket = TFS.idTicket and TFS.idSession = S.idSession;")
    fun getSessionByEmailAndTicket(idClient: Long, idTicket: Long): Session?

    @Query("DELETE FROM TicketsForClient WHERE idClient = :idClient and idTicket = :idTicket")
    fun deleteFromTFC(idClient: Long, idTicket: Long)


}