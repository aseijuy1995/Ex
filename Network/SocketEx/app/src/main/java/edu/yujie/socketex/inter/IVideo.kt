package edu.yujie.socketex.inter

import io.reactivex.rxjava3.core.Completable

interface IVideo {

    fun fetchVideo(): Completable

}