package edu.yujie.rxjavaex

import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject

//https://blog.csdn.net/mq2553299/article/details/78848773
//https://www.jianshu.com/p/1257c8ba7c0c
//https://prototypez.github.io/2016/04/30/rxjava-common-mistakes-1/
//https://www.jianshu.com/p/240f1c8ebf9d

class SubjectDoc {
    init {
        ReplaySubject.create<Int>()// 當呼叫subscribe()時,將所有數據發射給Observer
        PublishSubject.create<Int>()// 當呼叫subscribe()時,將訂閱後發射的數據給Observer
        BehaviorSubject.create<Int>()// 當呼叫subscribe()時,將訂閱前的最後一個數據以及後續發射的數據給Observer
        AsyncSubject.create<Int>()// 當subject執行onComplete()時,才會發射最後一個數據
    }
}