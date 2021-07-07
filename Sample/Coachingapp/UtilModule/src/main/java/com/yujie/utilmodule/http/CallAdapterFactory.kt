package com.yujie.utilmodule.http

import retrofit2.CallAdapter
import retrofit2.adapter.guava.GuavaCallAdapterFactory
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

enum class CallAdapterFactory(val factory: CallAdapter.Factory) {
    GuavaFactory(GuavaCallAdapterFactory.create()),
    RxJavaFactory(RxJavaCallAdapterFactory.create()),
    RxJava2Factory(RxJava2CallAdapterFactory.create()),
    RxJava3Factory(RxJava3CallAdapterFactory.create()),
//    ScalaFactory(ScalaCallAdapterFactory.create()),
}