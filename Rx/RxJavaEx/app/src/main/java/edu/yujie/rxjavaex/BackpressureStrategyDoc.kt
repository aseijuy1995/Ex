package edu.yujie.rxjavaex

import io.reactivex.rxjava3.core.BackpressureStrategy


//https://www.jianshu.com/p/99546492e328
//https://zhuanlan.zhihu.com/p/68278195
//https://juejin.cn/post/6844903454067032071

class BackpressureStrategyDoc {
    init {
        BackpressureStrategy.MISSING// 沒有指定back pressure
        BackpressureStrategy.ERROR// Buffer pool滿時,拋出MissingBackpressureException
        BackpressureStrategy.BUFFER// 不會發生丟失數據,不過當上游速度大於下游處理速度時,Buffer pool已滿會造成OOM
        BackpressureStrategy.DROP// Buffer pool滿的話會丟失後續將存數據,不在乎中間過程與最後發射數據處理的結果
        BackpressureStrategy.LATEST// Buffer pool滿的話會將最後一筆將存數據放入Buffer pool中,之間其餘丟失,不在乎中間過程,但會處理最後發射數據結果
    }
}