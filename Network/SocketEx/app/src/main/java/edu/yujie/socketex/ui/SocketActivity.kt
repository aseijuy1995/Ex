package edu.yujie.socketex.ui

import androidx.navigation.findNavController
import edu.yujie.socketex.R
import edu.yujie.socketex.databinding.ActivitySocketBinding


//https://juejin.cn/post/6844903461796970504
//https://notfalse.net/7/three-way-handshake
//https://www.jianshu.com/p/089fb79e308b
//https://www.jianshu.com/p/8175f51e662c
//https://blog.csdn.net/xlh1191860939/article/details/103216735
//https://www.jianshu.com/p/45d27f3e1196
//https://www.jianshu.com/p/a6d086a3997d

class SocketActivity : BaseActivity<ActivitySocketBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_socket

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.frag_container_view).navigateUp()

    companion object {
        val REQUEST_CODE_CAPTURE = 1001//拍照
        val REQUEST_CODE_ALBUM = 1002//相簿
        val REQUEST_CODE_AUDIO = 1003//音訊
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        println("onActivityResult:$requestCode, $resultCode")
//        super.onActivityResult(requestCode, resultCode, data)
//        supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.fragments?.forEach {
//            it.onActivityResult(requestCode, resultCode, data)
//        }
//    }


}