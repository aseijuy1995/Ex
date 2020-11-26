package edu.yujie.pagingex

import edu.yujie.pagingex.constant.Concert
import kotlinx.coroutines.delay

interface ApiService {
    suspend fun load(fromIndex: Int, toIndex: Int): List<Concert>
}


class ApiServiceImpl : ApiService {
    val list = mutableListOf<Concert>()

    init {
        for (i in 1..147) {
            val concert = Concert(id = i, name = "Name = $i")
            list.add(concert)
        }
    }

    override suspend fun load(fromIndex: Int, toIndex: Int): List<Concert> {
        delay(500)
        return when {
            fromIndex > list.size -> emptyList()
            toIndex > list.size -> list.subList(fromIndex = fromIndex, toIndex = list.size)
            else -> list.subList(fromIndex = fromIndex, toIndex = toIndex)
        }
    }
}