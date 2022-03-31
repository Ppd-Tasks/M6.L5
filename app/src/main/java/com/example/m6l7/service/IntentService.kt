package com.example.m6l7.service

import android.app.IntentService
import android.content.Intent
import android.widget.Toast

public class IntentService : IntentService("my_intent_thread"){

    override fun onHandleIntent(intent: Intent?) {
        synchronized (this){
            try{
                Thread.sleep(20000)
            }catch(e:InterruptedException){
                e.printStackTrace()
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Intent Service Started", Toast.LENGTH_SHORT).show()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Toast.makeText(this, "Intent Service Destroyed", Toast.LENGTH_SHORT).show()
        super.onDestroy()

    }



}