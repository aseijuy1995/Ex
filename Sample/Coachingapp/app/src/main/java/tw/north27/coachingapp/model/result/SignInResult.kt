package tw.north27.coachingapp.model.result

data class SignInResult(
    val guid: Long,
    val account: String,
    val accessToken: String,
    val identity: Identity,// 身分
    val refreshToken: String,//獲取新token所用
    val expiredTime: String,// 過期時間
    val isFirstSignIn: Boolean,// 第一次登入
    val userProfile: UserProfile,// 用戶資訊
    val signInType: SignInType
)

enum class SignInState {
    SUCCESS,// 成功
    FAILURE;// 失敗
}

enum class Identity {
    STUDENT,// 學生
    TEACHER,// 老師
    LITTLE_HELPER,// 小幫手
    VOLUNTEER// 志工
}

data class UserProfile(
    val id: Int,
    val avatarPath: String,
    val name: String,
    val nickName: String,
    val email: String,
    val registerTime: String// 註冊時間
)

enum class SignInType {
    DEFAULT,
    AUTO,
    EMAIL,
    LOGOUT,
    AUTO_EMAIL,
    AUTO_LOGOUT,
}