package com.prongbang.readsms.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast

class SmsBroadcastReceiver : BroadcastReceiver() {

	override fun onReceive(context: Context?, intent: Intent) {
		if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
			var smsSender = ""
			var smsBody = ""
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
					smsSender = smsMessage.displayOriginatingAddress
					smsBody += smsMessage.messageBody
				}
			} else {
				val smsBundle = intent.extras
				if (smsBundle != null) {
					val pdus = smsBundle["pdus"] as? Array<Any>
					if (pdus == null) { // Display some error to the user
						Log.e(TAG, "SmsBundle had no pdus key")
						return
					}
					val messages: Array<SmsMessage?> = arrayOfNulls(pdus.size)
					for (i in messages.indices) {
						messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
						smsBody += messages[i]?.messageBody
					}
					smsSender = messages[0]?.originatingAddress ?: ""
				}
			}

			Log.i(TAG, "smsSender: $smsSender")
			Log.i(TAG, "smsBody: $smsBody")

			Toast.makeText(context, "smsSender: $smsSender\nsmsBody: $smsBody", Toast.LENGTH_SHORT)
					.show()
		}
	}

	companion object {
		private val TAG = SmsBroadcastReceiver::class.java.simpleName
	}

}