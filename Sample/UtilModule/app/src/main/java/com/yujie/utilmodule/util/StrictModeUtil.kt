package com.yujie.utilmodule.util

import android.os.Build
import android.os.StrictMode

/**
 * 啟用StrictMode
 * */
fun startStrictMode() {
		StrictMode.setThreadPolicy(
				StrictMode.ThreadPolicy.Builder().apply {
						detectAll()
						detectCustomSlowCalls()//啟用慢速通話檢測
						detectDiskReads()//啟用對磁盤讀取的檢測
						detectDiskWrites()//啟用對磁盤寫入的檢測
						detectNetwork()//啟用對網絡操作的檢測
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) detectResourceMismatches()//啟用檢測已定義資源類型和getter調用之間的不匹配的功能。
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) detectUnbufferedIo()//檢測未緩衝的輸入/輸出操作。
						penaltyDialog()//在檢測到的違規情況下向開發人員顯示一個煩人的對話框，速率限制只是一點煩人
						penaltyDropBox()//啟用檢測到的違規將堆棧跟踪和計時數據記錄為DropBox上策略違規
						penaltyLog()//將檢測到的違規記錄到系統日誌中
				}.build()
		)
		StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().apply {
				detectActivityLeaks()//檢測Activity子類的洩漏
				detectAll()
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) detectCleartextNetwork()//從呼叫應用程序檢測未包裝在SSL / TLS中的任何網絡流量
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) detectContentUriWithoutPermission()//在content:// Uri沒有設置Intent.FLAG_GRANT_READ_URI_PERMISSION或的情況下檢測調用應用程序何時將發送給另一個應用程序Intent.FLAG_GRANT_WRITE_URI_PERMISSION
				if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) detectCredentialProtectedWhileLocked()//當用戶被鎖定時，檢測對存儲在受憑證保護的存儲區中的文件系統路徑的訪問
				detectFileUriExposure()//檢測調用應用程序何時file:// Uri 向其他應用程序公開
				if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) detectImplicitDirectBoot()//檢測對Direct Boot自動過濾PackageManager值的任何隱式依賴
				detectLeakedClosableObjects()//檢測Closeable帶有顯式終止方法的對像或其他對象何時完成而不關閉
				detectLeakedRegistrationObjects()//在拆卸期間檢測BroadcastReceiver或ServiceConnection洩漏 Context
				detectLeakedSqlLiteObjects()//檢測一個SQLiteCursor或其他SQLite對象何時完成而不關閉
				if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) detectNonSdkApiUsage()//檢測不屬於公共Android SDK的API的反射用法
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) detectUntaggedSockets()//檢測調用應用程序中未使用標記的所有套接字TrafficStats
				penaltyDropBox()//啟用檢測到的違規將堆棧跟踪和計時數據記錄為DropBox上策略違規
				penaltyLog()//將檢測到的違規記錄到系統日誌中
		}.build())
}