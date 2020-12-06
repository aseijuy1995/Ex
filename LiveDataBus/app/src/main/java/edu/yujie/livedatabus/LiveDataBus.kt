package edu.yujie.livedatabus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

object LiveDataBus {
    val TAG = javaClass.simpleName
    val liveDataMap: HashMap<String, BaseMutableLiveData<Any?>> by lazy { HashMap() }

    fun send(tag: String, any: Any?) {
        val liveData = liveDataMap[tag]
        if (liveData == null) {
            println("$TAG send() : Cannot find the specified liveData in the liveDataMap")
        } else {
            liveData.postValue(any)
        }
    }

    inline fun <reified T : Any?> with(tag: String): BaseMutableLiveData<T>? {
        liveDataMap[tag] = BaseMutableLiveData()
        val liveData = liveDataMap[tag]
        return liveData as BaseMutableLiveData<T>?
    }


    class ObserverWrapper<T>(val observer: Observer<in T>) : Observer<T> {

        override fun onChanged(t: T) {
            if (isCallObserver()) {
                return
            }
            observer.onChanged(t)
        }

        private fun isCallObserver(): Boolean {
            val stackTrace = Thread.currentThread().stackTrace
            if (stackTrace.isNotEmpty()) {
                stackTrace.forEach {
                    if ("androidx.lifecycle.LiveData" == it.className && "observeForever" == it.methodName) {
                        return true
                    }
                }
            }
            return false
        }

    }

    class BaseMutableLiveData<T> : MutableLiveData<T>() {
        private val observerMap: HashMap<Observer<in T>, Observer<T>> = HashMap()

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
                observerMap[observer] = LiveDataBus.ObserverWrapper<T>(observer)
            }
            super.observeForever(observer)
        }

        override fun removeObserver(observer: Observer<in T>) {
            val realObserver: Observer<in T>? =
                if (observerMap.containsKey(observer)) {
                    observerMap.remove(observer)
                } else {
                    observer
                }
            super.removeObserver(realObserver!!)
        }

        private fun hook(observer: Observer<in T>) {
            //get wrapper's version
            val classLiveData = LiveData::class.java
            val fieldObservers = classLiveData.getDeclaredField("mObservers")
            fieldObservers.isAccessible = true
            val objectObservers = fieldObservers.get(this)
            val classObservers = objectObservers.javaClass
            val methodGet = classObservers.getDeclaredMethod("get", Object::class.java)
            methodGet.isAccessible = true
            val objectWrapperEntry = methodGet.invoke(objectObservers, observer)
            var objectWrapper: Any? = null
            if (objectWrapperEntry is Map.Entry<*, *>) {
                objectWrapper = objectWrapperEntry.value
            }
            if (objectWrapper == null) {
                throw  NullPointerException("Wrapper can not be bull!");
            }

            val classObserverWrapper = objectWrapper::class.java.getSuperclass()
            val fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion")
            fieldLastVersion.isAccessible = true
            //get livedata's version
            val fieldVersion = classLiveData.getDeclaredField("mVersion")
            fieldVersion.isAccessible = true
            val objectVersion = fieldVersion.get(this)
            //set wrapper's version
            fieldLastVersion.set(objectWrapper, objectVersion)
        }
    }


}