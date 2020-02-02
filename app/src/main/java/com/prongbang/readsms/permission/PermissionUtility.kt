package com.prongbang.readsms.permission

interface PermissionUtility {
	fun smsGranted(onGranted: () -> Unit)
}