package edu.yujie.socketex.ui

import androidx.navigation.findNavController
import edu.yujie.socketex.R
import edu.yujie.socketex.finish.base.activity.BaseViewBindingAppCompatActivity
import edu.yujie.socketex.databinding.ActivitySocketBinding


//https://juejin.cn/post/6844903461796970504
//https://notfalse.net/7/three-way-handshake
//https://www.jianshu.com/p/089fb79e308b
//https://www.jianshu.com/p/8175f51e662c
//https://blog.csdn.net/xlh1191860939/article/details/103216735
//https://www.jianshu.com/p/45d27f3e1196
//https://www.jianshu.com/p/a6d086a3997d


class SocketActivity : BaseViewBindingAppCompatActivity<ActivitySocketBinding>(ActivitySocketBinding::inflate) {

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.frag_container_view).navigateUp()

}