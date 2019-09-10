package com.example.prpsapp.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.prpsapp.notification.NotificationHelper.setNotification

class AlertReceiver: BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val text = intent?.getStringExtra(TEXT)
        val title = intent?.getStringExtra(TITLE)
        setNotification(context!!, title ?:"", text?:"")
    }

}