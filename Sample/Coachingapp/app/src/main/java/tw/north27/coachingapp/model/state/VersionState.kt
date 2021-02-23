package tw.north27.coachingapp.model.state

import tw.north27.coachingapp.model.result.VersionResult

data class VersionState(
    val isLoading: Boolean,
    val errorMsg: String?,
    val result: VersionResult?
) {

    companion object {
        fun initial(): VersionState = VersionState(
            isLoading = true,
            errorMsg = null,
            result = null
        )
    }

}