package edu.yujie.koinex.module

/**
 * Linking modules strategies?
 * */
interface DataSource {
    fun print()
}

class Repository(val dataSource: DataSource) {
    fun show() = dataSource.print()
}

class LocalDataSource : DataSource {
    private val TAG = javaClass.simpleName
    override fun print() {
        println("$TAG:print()")
    }
}


class RemoteDataSource : DataSource {
    private val TAG = javaClass.simpleName
    override fun print() {
        println("$TAG:print()")
    }
}

