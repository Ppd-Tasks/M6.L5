package com.example.m6l7.receiver

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.Toast
import com.example.m6l7.activity.MainActivity


class BootCompletedIntentReceiver : BroadcastReceiver(){

    override fun onReceive(p0: Context?, p1: Intent?) {
        /*if ("android.intent.action.BOOT_COMPLETED".equals(p1?.getAction())) {
            val pushIntent = Intent(p0, Service::class.java)
            p0?.startService(pushIntent);
            showToast(p0,"BootCompleted");
        }*/
        val intent = Intent(p0,MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        p0?.startActivity(intent)
        showToast(p0,"Boot Completed")
    }

    fun showToast(context: Context?, message: String?) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}