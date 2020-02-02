package com.prongbang.readsms.permission

import android.Manifest
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class DexterPermissionUtility(
		private val dexter: DexterBuilder.Permission
) : PermissionUtility {

	override fun smsGranted(onGranted: () -> Unit) {
		dexter.withPermissions(
				Manifest.permission.RECEIVE_SMS,
				Manifest.permission.READ_SMS,
				Manifest.permission.SEND_SMS)
				.withListener(onPermissionsListener {
					onGranted.invoke()
				})
				.check()
	}

	private fun onPermissionsListener(onGranted: () -> Unit): MultiplePermissionsListener {
		return object : MultiplePermissionsListener {
			override fun onPermissionsChecked(
					report: MultiplePermissionsReport) {
				onGranted.invoke()
			}

			override fun onPermissionRationaleShouldBeShown(
					permissions: List<PermissionRequest>,
					token: PermissionToken?) {
				token?.continuePermissionRequest()
			}
		}
	}
}