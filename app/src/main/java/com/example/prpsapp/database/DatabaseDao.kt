package com.example.prpsapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DatabaseDao {
    @Insert
    fun insertClient(night: Client)

    @Query("SELECT * FROM session")
    fun getAllNights(): LiveData<List<Session>>

}