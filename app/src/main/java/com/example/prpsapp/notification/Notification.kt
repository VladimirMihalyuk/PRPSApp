package com.example.prpsapp.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.prpsapp.MainActivity
import com.example.prpsapp.R
import com.example.prpsapp.WHEN_ALARM
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.*


val TITLE = "TITLE"
val TEXT = "TEXT"

val NAME = "NAME"
val DESCRIPTION = "DESCRIPTION"

object NotificationHelper{
    fun setNotification(context: Context, title: String, text: String){
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val CHANNEL_ID = "${context.packageName}-${context.getString(R.string.app_name)}"

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_app)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle())
        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channelId = "${context.packageName}-${context.getString(R.string.app_name)}"

            val channel = NotificationChannel(channelId, NAME, importance).apply {
                description = DESCRIPTION
            }

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun startAlarm(context: Context, date: Long, text: String, title: String){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlertReceiver::class.java)
        intent.putExtra(TITLE, title)
        intent.putExtra(TEXT, text)
        val pendingIntent = PendingIntent.getBroadcast(context, System.currentTimeMillis().toInt(), intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, date + WHEN_ALARM * 60 * 60 * 1000, pendingIntent)
    }
}

fun CalendarDay.getMillis(): Long{
    val date = Date(year - 1900, month - 1 , day)
    return date.time
}





