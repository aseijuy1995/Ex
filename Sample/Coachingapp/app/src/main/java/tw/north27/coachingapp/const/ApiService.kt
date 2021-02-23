package tw.north27.coachingapp.const


import kotlinx.coroutines.delay
import retrofit2.Response
import retrofit2.http.Field
import tw.north27.coachingapp.model.result.*

class ApiService : IApiService {
    override suspend fun getVersionCtrlResult(): Response<VersionResult> {
        delay(1500)
        return Response.success<VersionResult>(
            VersionResult(
                version = "1.0.0",
                apkDownloadUrl = "https://play.google.com/store/apps/details?id=ojisan.Droid&hl=zh_TW",
                updateLog = "1. 今天要加班(現在幾點了?)\n2. 噴灑殺蟲劑，殺死些Dug蟲蟲\n3. 泡茶休息下~~~\n\t請稍等...",
                targetSize = "5M",
                newMd5 = "A818AD325EACC199BC62C552A32C35F2",
                isMandatoryUpdate = false
            )
        )
    }

    override suspend fun checkSignIn(@Field(value = "account") account: String, @Field(value = "password") password: String?): Response<SignInResult> {
        delay(1500)
        return Response.success<SignInResult>(
            SignInResult(
                guid = 0,
                account = "Account001",
                accessToken = "accessToken001",
                identity = Identity.STUDENT,
                refreshToken = "refreshToken001",
                expiredTime = "2021/02/28",
                userProfile = UserProfile(
                    id = 0,
                    avatarPath = "data:image/jpeg;base64,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",
                    name = "North27",
                    nickName = "N27",
                    email = "b0972911675@north27.tw",
                    registerTime = "2021/02/22"
                ),
                isFirstSignIn = true,
                signInType = SignInType.DEFAULT
            )
        )
    }
}