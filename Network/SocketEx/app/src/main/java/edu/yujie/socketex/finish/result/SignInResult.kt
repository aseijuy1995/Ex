package edu.yujie.socketex.finish.result

data class SignInResultInfo(
//    val LoginType: LoginType,
    val account: String,
    val authToken: String,
    val authType: AuthType,
//    val expiredTime: String,
//    val isFirstLogin: Boolean,
    val memberGuid: String,
//    val memberProfileData: MemberProfileData,
//    val trialRemainingSeconds: Int
)

enum class LoginType {
    DEFAULT,
    AUTO,
    EMAIL,
    CELLPHONE,
    FACEBOOK_LOGIN,
    LOGOUT,
    FACEBOOK_REGISTER,
    AUTO_EMAIL,
    AUTO_FACEBOOK,
    AUTO_CELLPHONE,
    TO_REGISTER_CELLPHONE,
    TO_REGISTER_EMAIL;
}

enum class AuthType {
    UNKNOWN,
    FREE,
    MOBILE,
    COMPUTER;
}

data class MemberProfileData(
    val cellphone: String,
    val channelId: String,
    val channelImagePath: String,
    val contactEmail: String,
    val fbEmail: String,
    val fbImagePath: String,
    val fbUrl: String,
    val hasBindFb: String,
    val hasBindingCellphone: String,
    val hasBindingLoginEmail: String,
    val hasUnverifiedContactEmail: String,
    val headImagePath: String,
    val isUseFbImage: String,
    val loginEmail: String,
    val memberPk: String,
    val nickName: String,
    val registerTime: String
)