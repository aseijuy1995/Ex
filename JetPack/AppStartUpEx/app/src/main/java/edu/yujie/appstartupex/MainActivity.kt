package edu.yujie.appstartupex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author YuJie on 2020/10/17
 * @describe 初始化每個library時,避免各自庫生成ContentProvider,造成初始延遲,透過整合的InitializationProvider集中管理,達到效能優化
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 手動調用組件初始化程序
//        AppInitializer.getInstance(this).initializeComponent(WorkManagerInitializer::class.java)
//        AppInitializer.getInstance(this).initializeComponent(ExampleLoggerInitializer::class.java)

    }
}