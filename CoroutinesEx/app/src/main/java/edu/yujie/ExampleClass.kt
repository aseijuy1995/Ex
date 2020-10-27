package edu.yujie

import kotlinx.coroutines.*

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */
class ExampleClass {
    //CoroutineScope(SupervisorJob())的程序，以便在協程失敗時不傳播取消
    val scope = CoroutineScope(Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, t ->
        t.printStackTrace()
    })

    fun exampleMethod(){
        scope.launch{

        }
    }
}