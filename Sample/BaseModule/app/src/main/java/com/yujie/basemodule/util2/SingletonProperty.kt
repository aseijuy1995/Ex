package tw.north27.coachingapp.util2

open class SingletonProperty<T, S>(private val creator: (S) -> T) {
    @Volatile
    private var sInstance: T? = null

    fun get(arg: S): T = sInstance ?: synchronized(this) {
        sInstance ?: creator(arg).also {
            sInstance = it
        }
    }
}