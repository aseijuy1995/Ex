package tw.north27.coachingapp.model.media

import com.yujie.core_lib.model.MimeType

/**
 * 媒體配置
 * @param mimeType >> 媒體類型
 * @param isMultipleChoice >> 媒體類型
 * @param multipleChoiceMaxCount >> 媒體類型
 * */
data class MediaConfig(
    val mimeType: MimeType = MimeType.IMAGE,
    val isMultipleChoice: Boolean,
    val multipleChoiceMaxCount: Int,
)