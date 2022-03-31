package com.example.m6l7.activity

import android.content.*
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import com.example.m6l7.R
import com.example.m6l7.receiver.NetworkBroadCastReceiver
import com.example.m6l7.service.BoundService
import com.example.m6l7.service.StartedService
import android.content.Intent

import android.view.View
import com.example.m6l7.receiver.IncomingCallReceiver
import com.example.m6l7.service.IntentService

class MainActivity : AppCompatActivity() {
    var boundService:BoundService? = null
    var isBound = false
    val number = 0
    lateinit var receiver:NetworkBroadCastReceiver
    lateinit var callReceiver:IncomingCallReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        val btn_startStartedService:Button = findViewById(R.id.btn_startStartedService)
        val btn_stopStartedService:Button = findViewById(R.id.btn_stopStartedService)
        val btn_startBoundService:Button = findViewById(R.id.btn_startBoundService)
        val btn_stopBoundService:Button = findViewById(R.id.btn_stopBoundService)
        val btn_startIntentService:Button = findViewById(R.id.btn_startIntentService)
        val btn_stopIntentService:Button = findViewById(R.id.btn_stopIntentService)
        val btn_printTimeStap:Button = findViewById(R.id.btn_printTimeStap)
        val textView:TextView = findViewById(R.id.textView)

        btn_startStartedService.setOnClickListener {
            startStartedService()
        }
        btn_stopStartedService.setOnClickListener {
            stopStartedService()
        }

        btn_startBoundService.setOnClickListener {
            startBoundService()
        }
        btn_stopBoundService.setOnClickListener {
            stopBoundService()
        }
        btn_printTimeStap.setOnClickListener {
            if (isBound) {
                textView.setText(boundService!!.timestamp)
            }
        }

        btn_startIntentService.setOnClickListener {
            startIntentService()
        }
        btn_stopIntentService.setOnClickListener {
            stopIntentService()
        }

        receiver = NetworkBroadCastReceiver()
        //callReceiver = IncomingCallReceiver()

    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(receiver,filter)

        //val filterCall = IntentFilter(Intent)
        //registerReceiver(callReceiver,filterCall)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        //unregisterReceiver(callReceiver)
    }

    fun startStartedService(){
        startService(Intent(this,StartedService::class.java))
    }
    fun stopStartedService(){
        stopService(Intent(this,StartedService::class.java))
    }

    fun startBoundService(){
        val intent = Intent(this,BoundService::class.java)
        bindService(intent,mServiceConnection, BIND_AUTO_CREATE)
    }
    fun stopBoundService(){
        if (isBound){
            unbindService(mServiceConnection)
            isBound = false
        }
    }

    fun startIntentService() {
        val intent = Intent(this, IntentService::class.java)
        startService(intent)
    }

    fun stopIntentService() {
        val intent = Intent(this, IntentService::class.java)
        stopService(intent)
    }

    private val mServiceConnection:ServiceConnection = object : ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val myBinder:BoundService.MyBinder = p1 as BoundService.MyBinder
            boundService = myBinder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound= false
        }

    }
}