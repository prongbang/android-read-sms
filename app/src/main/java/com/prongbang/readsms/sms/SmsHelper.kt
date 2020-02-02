package com.prongbang.readsms.sms

import android.content.Context
import android.net.Uri
import android.telephony.SmsManager
import android.util.Patterns

object SmsHelper {

	fun isValidPhoneNumber(phoneNumber: String): Boolean {
		return Patterns.PHONE.matcher(phoneNumber)
				.matches()
	}

	fun sendDebugSms(number: String?, smsBody: String?) {
		val smsManager: SmsManager = SmsManager.getDefault()
		smsManager.sendTextMessage(number, null, smsBody, null, null)
	}

	fun getSmsInbox(context: Context, onResult: (String) -> Unit) {
		val smsInboxCursor = context.contentResolver.query(Uri.parse("content://sms/inbox"), null,
				null, null, null)
		smsInboxCursor?.let { it ->
			val indexBody: Int = it.getColumnIndex("body")
			val indexAddress: Int = it.getColumnIndex("address")
			if (indexBody < 0 || !it.moveToFirst()) return
			do {
				val text = "SMS From: " + it.getString(
						indexAddress).toString() + "\n" + it.getString(indexBody).toString() + "\n"
				onResult.invoke(text)
			} while (it.moveToNext())
		}
		smsInboxCursor?.close()
	}
}