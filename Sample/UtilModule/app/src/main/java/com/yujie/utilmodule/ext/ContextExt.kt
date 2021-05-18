package com.yujie.utilmodule.ext

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build

//@SuppressLint("ServiceCast")
//fun FragmentActivity.imel() {
//		val rxPermissions = RxPermissions(this)
//		rxPermissions
//				.request(Manifest.permission.READ_PHONE_STATE)
//				.subscribe {
////						if (it) {
////								val telephonyManager = getSystemService(Context.TELECOM_SERVICE) as TelephonyManager
////								val mDeviceIMEI: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////										telephonyManager.getImei()
////								} else {
////										telephonyManager.getDeviceId()
////								}
////						}
//				}
//}

//fun Context.startNetworkReceive(networkCallback: ConnectivityManager.NetworkCallback) {
//		val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//				val request = NetworkRequest.Builder()
//						.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//						.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
//						.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
//						.build()
//				connectivityManager.registerNetworkCallback(request, networkCallback)
//		}
//}
