package com.example.m6l7.receiver

import android.app.IntentService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class NetworkBroadCastReceiver : BroadcastReceiver(){

    override fun onReceive(p0: Context?, p1: Intent?) {
        /*if (Intent.ACTION_CALL == p1?.action){
            Toast.makeText(p0, "ACTION_CALL", Toast.LENGTH_SHORT).show()
        }*/

        /*if (isConnectingToInternet(p0!!)){
            Toast.makeText(p0, "Battery changed", Toast.LENGTH_LONG).show()
        }*/

        if (isConnectingToInternet(p0!!)){
            Toast.makeText(p0, "Called To Someone", Toast.LENGTH_LONG).show()
        }

    }

    fun isConnectingToInternet(context: Context):Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}