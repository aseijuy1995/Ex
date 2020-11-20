package edu.yujie.pagingex

import edu.yujie.pagingex.constant.Concert
import kotlinx.coroutines.delay

interface ApiService {
    suspend fun load(fromIndex: Int, toIndex: Int): List<Concert>
}

class ApiServiceImpl : ApiService {

    override suspend fun load(fromIndex: Int, toIndex: Int): List<Concert> {
        delay(1500)
        val list = mutableListOf<Concert>()
        for (i in fromIndex..toIndex) {
            val concert = Concert(id = i, name = "Name = $i")
            list.add(concert)
        }
        return list
    }
}