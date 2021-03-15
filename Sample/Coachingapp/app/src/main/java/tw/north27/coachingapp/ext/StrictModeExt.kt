package tw.north27.coachingapp.ext

import android.os.StrictMode

fun startStrictMode() {
    StrictMode.setThreadPolicy(StrictModeThreadPolicy)
    StrictMode.setVmPolicy(StrictModeVmPolicy)
}

val StrictModeThreadPolicy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().apply {
    detectDiskReads()//磁盤讀取
    detectDiskWrites()//磁盤寫入
    detectNetwork()//網絡操作
    detectResourceMismatches()//已定義資源類型與getter調用之間的不匹配
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) detectUnbufferedIo()//未緩衝的輸入/輸出操作
    penaltyDialog()//開發人員顯示一個煩人的對話框
    penaltyDropBox()//啟用檢測到的違規將堆棧跟踪和計時數據記錄為DropBox上策略違規
    penaltyFlashScreen()//閃爍屏幕
    penaltyLog()//系統日誌
}.build()

val StrictModeVmPolicy: StrictMode.VmPolicy = StrictMode.VmPolicy.Builder().apply {
    detectActivityLeaks()//檢測Activity子類的洩漏
    detectCleartextNetwork()//從呼叫應用程序檢測未包裝在SSL / TLS中的任何網絡流量
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) detectContentUriWithoutPermission()//在content:// Uri沒有設置Intent#FLAG_GRANT_READ_URI_PERMISSION或的情況下檢測調用應用程序何時將發送給另一個應用程序Intent#FLAG_GRANT_WRITE_URI_PERMISSION
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) detectCredentialProtectedWhileLocked()//當用戶被鎖定時，檢測對存儲在受憑證保護的存儲區中的文件系統路徑的訪問
    detectFileUriExposure()//檢測調用應用程序何時file:// Uri 向其他應用程序公開
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) detectImplicitDirectBoot()//檢測對Direct Boot自動過濾PackageManager值的任何隱式依賴
    detectLeakedClosableObjects()//檢測Closeable帶有顯式終止方法的對像或其他對象何時完成而不關閉
    detectLeakedRegistrationObjects()//在拆卸期間檢測BroadcastReceiver或ServiceConnection洩漏 Context
    detectLeakedSqlLiteObjects()//檢測一個SQLiteCursor或其他SQLite對象何時完成而不關閉
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) detectNonSdkApiUsage()//檢測不屬於公共Android SDK的API的反射用法
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) detectUntaggedSockets()//檢測調用應用程序中未使用標記的所有套接字TrafficStats
    penaltyDropBox()//啟用檢測到的違規將堆棧跟踪和計時數據記錄為DropBox上策略違規
    penaltyLog()//將檢測到的違規記錄到系統日誌中
}.build()