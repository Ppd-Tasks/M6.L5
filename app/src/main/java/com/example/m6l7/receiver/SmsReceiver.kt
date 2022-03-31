package com.example.m6l7.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.Toast
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import android.os.Build

class SmsReceiver:BroadcastReceiver() {

    private val TAG: String = SmsReceiver::class.java.getSimpleName()
    val pdu_type = "pdus"

    override fun onReceive(context: Context?, intent: Intent?) {
        // Get the SMS message.
        // Get the SMS message.
        val bundle: Bundle = intent?.getExtras()!!
        val msgs: Array<SmsMessage?>
        var strMessage = ""
        val format = bundle.getString("format")
        // Retrieve the SMS message received.
        // Retrieve the SMS message received.
        val pdus = bundle[pdu_type] as Array<Any>?
        if (pdus != null) {
            // Check the Android version.
            val isVersionM = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            // Fill the msgs array.
            msgs = arrayOfNulls(pdus.size)
            for (i in msgs.indices) {
                // Check Android version and use appropriate createFromPdu.
                if (isVersionM) {
                    // If Android version M or newer:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray, format)
                    }
                } else {
                    // If Android version L or older:
                    msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                }
                // Build the message to show.
                strMessage += "SMS from " + msgs[i]?.getOriginatingAddress()
                strMessage += """ :${msgs[i]?.getMessageBody()}
"""
                // Log and display the SMS message.
                Log.d(TAG, "onReceive: $strMessage")
                Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun showToast(context: Context?, message: String?) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}