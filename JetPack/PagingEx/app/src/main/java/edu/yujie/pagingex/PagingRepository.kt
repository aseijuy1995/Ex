package edu.yujie.pagingex

class PagingRepository {
    private val list = mutableListOf<Concert>()

    init {
        //data
        for (i in 1..1000 step 2) {
            val concert = Concert(id = i, name = "Name = $i")
            list.add(concert)
        }
    }

    fun load(position: Int, count: Int): List<Concert>? =
        when {
            position >= list.size - 1 -> null
            position + count > list.size -> list.subList(position, list.size)
            else -> list.subList(position, count)
        }

    fun loadMore(){
        for (i in 1001..2000 step 3) {
            val concert = Concert(id = i, name = "More:Name = $i")
            list.add(concert)
        }
    }

}