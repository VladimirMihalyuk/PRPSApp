package com.example.prpsapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    @PrimaryKey(autoGenerate = true)
    var idTFC: Long = 0L,

    var idClient: Long,

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
    @PrimaryKey(autoGenerate = true)
    var idTFS: Long = 0L,

    var idTicket: Long,

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

data class BuyTicketsQueryFirst(
    @ColumnInfo(name = "idTicket")
    val idTicket: Long,

    @ColumnInfo(name = "specialCode")
    val specialCode: String,

    @ColumnInfo(name = "time")
    val time: Long,

    @ColumnInfo(name = "cinema")
    val cinema: String
)

data class ReturnTicketsQueryFirst(
    @ColumnInfo(name = "idTicket")
    val idTicket: Long,

    @ColumnInfo(name = "time")
    val time: Long,

    @ColumnInfo(name = "cinema")
    val cinema: String,

    @ColumnInfo(name = "nameOfFilm")
    val nameOdFilm: String,

    @ColumnInfo(name = "image")
    val img: String,

    @ColumnInfo(name = "tickets")
    val tickets: Long
)






