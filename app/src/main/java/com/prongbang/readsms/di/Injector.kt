package com.prongbang.readsms.di

import android.app.Activity
import com.karumi.dexter.Dexter
import com.prongbang.readsms.permission.DexterPermissionUtility
import com.prongbang.readsms.permission.PermissionUtility

object Injector {

	fun provideDexter(activity: Activity) = Dexter.withActivity(activity)

	fun providePermissionUtility(activity: Activity): PermissionUtility {
		return DexterPermissionUtility(provideDexter(activity))
	}
}