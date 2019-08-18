package com.example.prpsapp.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DatabaseDao {

    @Insert
    fun insertClient(night: Client)

    @Query("SELECT * FROM session")
    fun getAllSessions(): LiveData<List<Session>>

    @Query("SELECT *FROM Client WHERE email = :newEmail")
    fun getClientsWithEmail(newEmail: String): List<Client>

}