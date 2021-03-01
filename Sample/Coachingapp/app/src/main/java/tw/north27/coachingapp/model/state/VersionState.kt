package tw.north27.coachingapp.model.state

import tw.north27.coachingapp.model.result.UpdateInfo

data class VersionState(
    val isLoading: Boolean,
    val errorMsg: String?,
    val result: UpdateInfo?
) {

    companion object {
        fun initial(): VersionState = VersionState(
            isLoading = true,
            errorMsg = null,
            result = null
        )
    }

}