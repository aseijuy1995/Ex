package com.yujie.core_lib.model

/**
 * 媒體資訊
 * @param id >> 媒體id
 * @param mimeType >> 媒體類型
 * @param defaultSortOrder >> 媒體來源
 * @param path >> 文件路徑
 * @param displayName >> 文件名
 * @param size >> 大小
 * @param height >> 高
 * @param width >> 寬
 * */
open class Media(
    open val id: Int,
    open val mimeType: String,
    open val defaultSortOrder: String,
    open val path: String,
    open val displayName: String,
    open val size: Long,
    open val height: Int,
    open val width: Int,
)