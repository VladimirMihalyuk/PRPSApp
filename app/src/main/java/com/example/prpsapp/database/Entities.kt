package com.example.prpsapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Client(
    @PrimaryKey(autoGenerate = true)
    var idClient: Long = 0L,

    var fullName: String,

    var phoneNumber: String,

    val email: String,

    var password: String
)

@Entity
data class TicketsForClient(
    var idClient: Long,

    @PrimaryKey
    var idTicket: Long
)

@Entity
data class Ticket(
    @PrimaryKey(autoGenerate = true)
    var idTicket: Long = 0L,

    val specialCode: String,

    val time: Long,

    var cinema:String
)

@Entity
data class TicketsForSession(
    var idTicket: Long,

    @PrimaryKey
    var idSession: Long
)

@Entity
data class Session(
    @PrimaryKey(autoGenerate = true)
    var idSession: Long = 0L,

    var description: String,

    var duration: Long,

    var ticketsLeft: Long,

    var image: String,

    var nameOfFilm: String
)

data class BuyTicketsQuerySecond(
    @ColumnInfo(name = "specialCode")
    val specialCode: String,

    @ColumnInfo(name = "time")
    val time: Long,

    @ColumnInfo(name = "cinema")
    val cinema: String
)



