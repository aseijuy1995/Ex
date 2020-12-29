package singleton

open class Singleton {
    open fun work() {}
}

class SingletonEx : Singleton() {
    override fun work() {
        super.work()
    }
}

//餓漢模式
class SingletonHungryMan private constructor() : Singleton() {
    companion object {
        private val instance = SingletonHungryMan()

        fun get(): SingletonHungryMan = instance
    }
}

//懶漢模式 - 無論是否初始過都須進行同步,導致降低速度
class SingletonLazyMan private constructor() : Singleton() {
    companion object {
        private var instance: SingletonLazyMan? = null

        @Synchronized
        fun get(): SingletonLazyMan {
            if (instance == null) {
                instance = SingletonLazyMan()
            }
            return instance!!
        }
    }
}

//DCL
/**
 * 一般初始化會執行3步驟
 * 1.給初始化的物件分配記憶體
 * 2.初始化類別
 * 3.將物件指向分配的記憶體"空間"中
 * 但Java編譯器允許亂序執行所以2、3順序是無法保證,可能執行順序為1-2-3、1-3-2
 * 若執行順序為1-3-2,可能會導致初始化時執行1-3,已經將物件指向記憶體"空間",但實際上還未初始化類別
 * 若切換執行續使用此物件就會出錯(此為JDK1.5前)
 * JDK1.5後可添加volatile關鍵字,可保證每次讀取都是直接從記憶體中取得
 * */
class SingletonDCL private constructor() : Singleton() {
    companion object {
        @Volatile
        private var instance: SingletonDCL? = null

        fun get(): SingletonDCL {
            if (instance == null) {
                synchronized(SingletonDCL::class) {
                    if (instance == null) {
                        instance = SingletonDCL()
                    }
                }
            }
            return instance!!
        }
    }
}

//靜態內部單例
/**
 * 第一次加載時不會初始化instance,只有在第一次調用時才會初始
 * 因為第一次調用JVM會先加載SingletonHolder類.這種方法可保證執行續安全也能保證物件唯一性,延遲單例的初始化
 * 為推薦使用方式
 * */
class SingletonStaticInner private constructor() : Singleton() {
    companion object {
        fun get(): SingletonStaticInner = SingletonHolder.instance
    }

    class SingletonHolder {
        companion object {
            val instance: SingletonStaticInner = SingletonStaticInner()
        }
    }
}

//枚舉單例
//默認執行域安全
enum class SingletonEnum {
    INSTANCE
}

//kotlin-object
object SingletonObject : Singleton() {
    override fun work() {
        super.work()
    }
}