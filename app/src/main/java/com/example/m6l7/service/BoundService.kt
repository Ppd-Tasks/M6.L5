package com.example.m6l7.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.SystemClock
import android.widget.Chronometer
import android.widget.Toast

class BoundService : Service() {
    private val eBinder:IBinder = MyBinder()
    private var eChronometer:Chronometer? = null

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "Bound Service Created", Toast.LENGTH_SHORT).show()
        eChronometer = Chronometer(this)
        eChronometer!!.base = SystemClock.elapsedRealtime()
        eChronometer!!.onChronometerTickListener = Chronometer.OnChronometerTickListener { chronometer ->
            Toast.makeText(application, chronometer.base.toString(), Toast.LENGTH_SHORT).show()
        }
        eChronometer!!.start()
    }

    override fun onBind(p0: Intent?): IBinder {
        Toast.makeText(this, "Bound Service onBind", Toast.LENGTH_SHORT).show()
        return eBinder
    }

    override fun onRebind(intent: Intent?) {
        Toast.makeText(this, "Bound Service onReBind", Toast.LENGTH_SHORT).show()
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Toast.makeText(this, "Bound Service onUnBind", Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Bound Service Stopped", Toast.LENGTH_SHORT).show()
        eChronometer!!.stop()
    }

    companion object{

    }

    val timestamp:String
    get() {
        val elapsedMills = SystemClock.elapsedRealtime() - eChronometer!!.base
        val hours = (elapsedMills / 3600000).toInt()
        val minutes = (elapsedMills - hours * 3600000).toInt() / 1000
        val seconds = (elapsedMills - hours * 3600000 - minutes * 60000).toInt() / 1000
        val mills = (elapsedMills - hours * 3600000 - minutes * 60000 - seconds * 1000).toInt()
        return "$hours:$minutes:$seconds:$mills"
    }

    inner class MyBinder : Binder(){
        fun getService(): BoundService = this@BoundService
    }
}