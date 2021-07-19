package tw.north27.coachingapp.model.media

import com.yujie.core_lib.model.Media

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
 * @param isSelect >> 是否選取
 * */

data class MediaData(
    override val id: Int,
    override val mimeType: String,
    override val defaultSortOrder: String,
    override val path: String,
    override val displayName: String,
    override val size: Long,
    override val height: Int,
    override val width: Int,
    var isSelect: Boolean = false
) : Media(
    id = id,
    mimeType = mimeType,
    defaultSortOrder = defaultSortOrder,
    path = path,
    displayName = displayName,
    size = size,
    height = height,
    width = width
)