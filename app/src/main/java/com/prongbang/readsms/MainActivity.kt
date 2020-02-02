package com.prongbang.readsms

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.prongbang.readsms.di.Injector
import com.prongbang.readsms.sms.SmsHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	private val TAG = MainActivity::class.java.simpleName

	private val permissionUtility by lazy { Injector.providePermissionUtility(this) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		permissionUtility.smsGranted {
			Log.i(TAG, "Permission Granted")
			SmsHelper.getSmsInbox(this) {
				Log.v(TAG, it)
			}
		}

		val phoneNumber = "Please enter phone number here!"

		btnSend.setOnClickListener {
			SmsHelper.sendDebugSms(phoneNumber, "Hello SMS")
		}
	}
}
