package edu.yujie.rxjavaex

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors

//https://android.jlelse.eu/rxjava-schedulers-what-when-and-how-to-use-it-6cfc27293add
//https://stackoverflow.com/questions/33370339/what-is-the-difference-between-schedulers-io-and-schedulers-computation

class SchedulersDoc {
    init {
        Schedulers.computation()// 默認thread數量等同於CPU數量,用於處理事件循環/處理回調/其他計算工作
        Schedulers.io()// 處理網路/讀寫文件等異步執行阻塞IO
        Schedulers.trampoline()// 在當前thread執行Task,會塞住thread
        Schedulers.single()//單線程依序順序執行Task
        Schedulers.newThread()//新建一個新的thread,大多使用Schedulers.io()即可
        Schedulers.from(Executors.newFixedThreadPool(10))//通過建立的thread pool執行Task
        AndroidSchedulers.mainThread()//Android Main Thread
    }
}