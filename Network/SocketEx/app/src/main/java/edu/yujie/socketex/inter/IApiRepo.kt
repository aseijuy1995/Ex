package edu.yujie.socketex.inter

import edu.yujie.socketex.bean.InitSettingBean

interface IApiRepo {

    /**
     * 初始數據
     * */
    suspend fun getInit(): InitSettingBean
}