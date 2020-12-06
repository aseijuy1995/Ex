package edu.yujie.rxbusex

data class Data(val id: Int, val name: String)

class DataEvent(val data: Data)

class DataListEvent(vararg val datas:Data)