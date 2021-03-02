package tw.north27.coachingapp.model.result

data class SignInInfo(
    val signInState: SignInState,
    val isFirst: Boolean,// 第一次登入
    val userInfo: UserInfo,// 用戶資訊
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

data class UserInfo(
    val id: Int,
    val account:String,
    val avatarPath: String,
    val name: String,
    val nickName: String,
    val email: String,
    val registerTime: String,// 註冊時間
    val accessToken: String,// 訪問用token
    val refreshToken: String// 刷新用token
)