package edu.yujie.socketex

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import edu.yujie.socketex.LiveDataBus.ObserverWrapper

object LiveDataBus {
    private val liveDataMap: HashMap<String, BaseMutableLiveData<Any?>> by lazy {
        HashMap<String, BaseMutableLiveData<Any?>>()
    }

    fun <T> with(key: String): BaseMutableLiveData<T> {
        if (!liveDataMap.containsKey(key)) {
            liveDataMap[key] = BaseMutableLiveData()
        }
        return liveDataMap[key] as BaseMutableLiveData<T>
    }


    class BaseMutableLiveData<T> : MutableLiveData<T>() {
        private val TAG = javaClass.simpleName

        private val observerMap = mutableMapOf<Observer<*>, Observer<*>>()

        fun observeSticky(owner: LifecycleOwner, observer: Observer<T>) =
            super.observe(owner, observer)

        fun observeForeverSticky(observer: Observer<in T>) =
            super.observeForever(observer)


        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, observer)
            try {
                hook(observer)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun observeForever(observer: Observer<in T>) {
            if (!observerMap.containsKey(observer)) {
                observerMap[observer] = ObserverWrapper(observer)
            }
            super.observeForever(observerMap[observer] as Observer<in T>)
        }

        override fun removeObserver(observer: Observer<in T>) {
            val realObserver: Observer<in T> = if (observerMap.containsKey(observer))
                observerMap.remove(observer) as Observer<in T>
            else
                observer
            super.removeObserver(realObserver)
        }

        private fun hook(observer: Observer<in T>) {
            val liveDataClass = LiveData::class.java

            val observersField = liveDataClass.getDeclaredField("mObservers").apply {
                isAccessible = true
            }
            val observers = observersField.get(this)
            val observersClass = observers.javaClass

            val methodGet = observersClass.getDeclaredMethod("get", Any::class.java).apply {
                isAccessible = true
            }
            val observerWrapperEntry = methodGet.invoke(observers, observer)
            var observerWrapper: Any? = null
            if (observerWrapperEntry is Map.Entry<*, *>) {
                observerWrapper = observerWrapperEntry.value
            }
            if (observerWrapper == null) {
                throw NullPointerException("$TAG hook() = Wrapper can not be null!")
            }
            val observerWrapperParentClass = observerWrapper.javaClass.superclass
            val lastVersionField = observerWrapperParentClass.getDeclaredField("mLastVersion").apply {
                isAccessible = true
            }
            val versionField = liveDataClass.getDeclaredField("mVersion").apply {
                isAccessible = true
            }
            val version = versionField[this]
            lastVersionField[observerWrapper] = version
        }

    }

    class ObserverWrapper<T>(val observer: Observer<T>) : Observer<T> {
        override fun onChanged(t: T) {
            if (isCallOnObserverForever())
                return
            observer.onChanged(t)
        }

        private fun isCallOnObserverForever(): Boolean {
            val stackTrace = Thread.currentThread().stackTrace
            stackTrace.forEach {
                if (it.className == "androidx.lifecycle.LiveData" && it.methodName == "observeForever")
                    return true
            }
            return false
        }

    }


}