package tw.north27.coachingapp.const


import kotlinx.coroutines.delay
import tw.north27.coachingapp.model.Data
import tw.north27.coachingapp.model.OriginateResult

class ApiService : IApiService {
    override suspend fun getOriginateData(): OriginateResult {
        delay(500)
        return OriginateResult(
            version = "1.0.0",
            apkDownloadUrl = "https://play.google.com/store/apps/details?id=ojisan.Droid&hl=zh_TW",
            updateLog = "1. 今天要加班(現在幾點了?)\n2. 噴灑殺蟲劑，殺死些Dug蟲蟲\n3. 泡茶休息下~~~\n\t請稍等...",
            targetSize = "5M",
            newMd5 = "A818AD325EACC199BC62C552A32C35F2",
            isMandatoryUpdate = false
        )
    }

    override suspend fun getData(): Data {
        return Data(true, 1, "1", 1)
    }


}