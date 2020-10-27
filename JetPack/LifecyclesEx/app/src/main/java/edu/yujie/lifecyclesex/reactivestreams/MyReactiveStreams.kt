package edu.yujie.lifecyclesex.reactivestreams

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.rxjava3.core.Flowable
import java.util.concurrent.TimeUnit

/**
 * @author YuJie on 2020/10/27
 * @describe 說明
 * @param 參數
 */
class MyReactiveStreams {

    val dataFlowable: Flowable<String> = Flowable.interval(3,TimeUnit.SECONDS).map { it.toString() }

    val dataLiveData: LiveData<String> = LiveDataReactiveStreams.fromPublisher(dataFlowable)
}