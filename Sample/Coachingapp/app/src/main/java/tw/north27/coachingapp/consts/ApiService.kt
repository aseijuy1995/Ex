package tw.north27.coachingapp.consts

import kotlinx.coroutines.delay
import retrofit2.Response
import retrofit2.http.Field
import tw.north27.coachingapp.model.result.*

class ApiService : IApiService {

    override suspend fun refreshToken(refreshToken: String): TokenInfo {
        delay(500)
        return TokenInfo(
            accessToken = "accessToken002",
            refreshToken = "refreshToken002"
        )
    }

    override suspend fun getAppConfig(token: String): AppConfig {
        delay(500)
        return AppConfig(
            appState = AppState.RUN,
//            maintainInfo = MaintainInfo(
//                desc = "1. 後端系統遭人入侵，正在極力維修\n2. 增加監控、效能分析、執行網路維護\n3. 描述統一規範化",
//                time = "2021/03/01",
//                newMd5 = ""
//            )
            updateInfo = UpdateInfo(
                versionName = "1.0.0",
                url = "https://play.google.com/store/apps/details?id=ojisan.Droid&hl=zh_TW",
                desc = "1. 今天要加班(現在幾點了?)\n2. 噴灑殺蟲劑，殺死些Dug蟲蟲\n3. 泡茶休息下~~~\n\t請稍等...",
                size = "5M",
                md5 = "A818AD325EACC199BC62C552A32C35F2",
                isMandatory = false
            )
        )
    }

    override suspend fun postCheckSignIn(account: String, deviceId: String, fcmToken: String): Response<SignInInfo> {
        delay(500)
        return Response.success<SignInInfo>(
            SignInInfo(
                signInState = SignInState.SUCCESS,
                isFirst = false,
                userInfo = UserInfo(
                    id = 0,
                    account = "account001",
                    avatarPath = "data:image/jpeg;base64,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",
                    name = "North27",
                    nickName = "N27",
                    email = "b0972911675@north27.tw",
                    registerTime = "2021/02/22",
                    accessToken = "accessToken001",
                    refreshToken = "refreshToken001",
                )
            )
        )
    }

    override suspend fun postSignIn(account: String, password: String, deviceId: String, fcmToken: String): Response<SignInInfo> {
        delay(1500)
        return Response.success<SignInInfo>(
            SignInInfo(
                signInState = SignInState.FAILURE,
                isFirst = true,
                userInfo = UserInfo(
                    id = 0,
                    account = "account001",
                    avatarPath = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxISEhUSEBIQFhAWFRUXFRUXFRYVEBUYFRUWFxUVGBUYHSggGBolGxUWITEhJSkrLjAuGB8zODMtNygtLisBCgoKDg0OGBAQFy0dHR0rKy0tLS0tLS0tLS0rKystKy0tLSstKystLS0tLTIrKy03LS0tNy03Ky0rLSsrNysrK//AABEIAOEA4QMBIgACEQEDEQH/xAAcAAEAAQUBAQAAAAAAAAAAAAAAAQIDBAUHBgj/xABEEAACAQIEAwUEBgcGBgMAAAABAgADEQQSITEFQVEGYXGBkRMiMqEHQlKCscEjYnKS0eHwFCQzU7PSNXOTorLxRFSD/8QAGAEBAQEBAQAAAAAAAAAAAAAAAAEDAgT/xAAfEQEBAQEAAwEBAQEBAAAAAAAAAQIRAyExEkFRYSL/2gAMAwEAAhEDEQA/AOwRESqREQEREBERARE1+I45hUJD4igpG4NRQR5XgbCJ5Tifb/CUtKZas36gsnmzW+V547i30iYuppSyUV/VAZ/Nm/ICdTNqddcifPuJ7QYtjdsViv8ArVAPIBrCYx41iv8A7WL/AOvV/wB0v4OvouJw7g/0gY6gbNU9sn2at2byqfF63nQeA/SLhMRZahNCodLVP8MnuqDT9605uar2EQGBFxYg7EbGJAiIgIiICIiAiIgIiICIiAiIgIiICIiAiPw+U5x2x7a581DCNanqHqg+83Iqh5D9bny6mydFzt92lZmGHwtSyi/tnU89LUww3tre3geYng2RByF+p1P8vKUNXtt6Sw9WbScjlFbu9OUw3qS+zyzUW/j1hFstKZQd7GVLCpiIkG+7N9rsVgiBTfNRvrRfWmf2Tunl6Gda7Nds8LjLKrezr/5T6N907P5a904PAYggi4I1BBsQRsQeRnNyr6bicy7CfSDcjD49xfQU650v+rUO1+jeR6npt5nZwIiIUiIgIiICIiAiIgIiICIiAiJo+2fGv7HhXqj/ABDZKX7bXsfIAt92B5D6Su1NycHQbQf4zDmd/Zg9Ovp1nPTUlpnJJJuSSSSdySbkk9TIvNpOOauF5SWlJkTpAyJMWgW6iX8eUsK0y5j4hba+v8ZKqLyQZbvJvIKyZBMpkXgSxnvfo/7e/wBny4bFsTh9kqHU0einrT/8fDbn5aUZr7evKTnR9SI4IBBBBFwRsQdiJM+c+HdqMdQRaVHE1VprfKvulRck2FwdNdpsqfb/AIiP/kk+KU/9s5/Cu9ROHr9ImO/zdetk/wBsyKf0kY/7dLzprf5SfinXaInHx9ImMP1kHgi/mDL1Lt7jP8z1Sn/tl/FOutyJy/C9rMdWOVKgzf8ALS1ybKNtySBOomc2cWIiIkCIiAiIgJyb6W+J58RTw6n3aS5mHLO/5hQP3p1mfPXHcb7fE1q326jkfs5iF/7QJ1ie0rAESbRaaoiJNogREmRASGW4sZMgtAwmFjaReXcSt9en4TGZwOl+/SQV5pTnvtr+EKnXX8JXaBQE6/yl4SkReAMSLyYFQMuI8syQZRmU3mdgkaowRBdidB+JPQd8xuG8NrVjamhtzci1Mfe/IToPAuC+yAp0gGrvux+ZPRF3/iSJLeI2/YrggVgd1pG7H7dUjTyUG9u9e+e5mPw/BrRprTXYbk7sTqWPeTeZMx1eu4iJMmQUREShERAtYskU3I3yNbxym0+cKew8BPpSfOvE8L7GtVo7ezqOlu5WIHytO8fUqxaTIzyC81RVKTKS8pJgVEygvIvIJkEkwoJNgLmV0aV9W0W9rc/PpMpBZSBuNP4H5y8GLg6ebVtuQ5ecvhL3HeBKsOgFwOVh6CVgWuepl4LdSmnNVsBc6CY2HwyHQgX+I7gi97AW8DL1VwamTroenM29JvMBwH2mFr4hPjolL96ZSanmLhvIzm2RZOvO08Ih2Lc+ff3yh8IA1szW8v4TIwg1IlbLmVvEkeR/lL6FrDcNDGxZtCR9X+HS02WB7OK5UO7jMmYWy93Ud/yMt8Oa7HrYE/h+U3eFq2RWO9NjfqUuxB9MwmkzOOLfa1S7H0frVKp7vdH5TbYHgOHpm60wT1b3j89pvcNwfEVPhpEDq5yD56/KbjB9l7a1ql/1U0H7519AJlrUjppcJhXc5aa3PoB3k8hPWcE4UKAJJzVW+JuXcq9FHz3mdhcOtNcqKFXoPxPUy9aY3XVkLSLSqJy6U2iVRAoiIlQiIgJxX6UcB7LHs4Hu1kWoOlx7rD1Ues7VPEfSxwc1sKK6D36DXPU020f0IQ+AM6zeVHH80ZpTE1RN4JlDPbx6RTINw2+lhyt+cAHuQBz58v5zMGDYDOiuy2N2CswA5gkCwmw7DcPGIx6IxYU6aPVcglTlTQDMNRq67crzqVHjjU1IZEpopy06KBWLAb6JqPl4TLfk/LTPj/TjWHN1J8/MS6j3PyPiNR6i/pN522pBcU7hSoqqtTKbXUkZWBtpfMpmjy3II3I8jNc67OuLOXiaR95vL8IxLkCw3NvAa7mV0aRLNfQm0x0U5WQ8r/1+EfpZll4HCBhWa1yiU2B3b3q9NCfGzHynSexXC65wYNOpSVKrOWVqeYnXJqeYss8RwPCVSmJemUyCnSRlIuzlqoyhe+6j1nt8ZgcZgsAzmph3WhRLez9kbkgXIzX6k62mO7301mZHguK8CfCVatJtQhXK32qbg5T6i3jea+mbKPBT6nWe7xvD69TCtWrU1V8pY5Cxpmm65ldQScuU7jzsOfhuIKABYge7z05g6d+n4zrGupcqsJZGY8st/Q3/ADnQeznCqlM1MNWQZ62FDLpoSpBFj1Bc3HUd4nhKVhVpNbQ2J53scxHmAZ3jIlT2dVbG3vIw6OtiPAg/hNd7uZxjz2qwKMtNFb4gqg89QOsvxBnndcBJlMXhVV4vKYkFV4lMQEREqEREBKatMMpVgCpBBB2IIsRKogfOvHsCtDEVKSEtSDn2bH6yfVN+dtRfnlvNcFLbXA6+u07Z2m7FUawaqq3cLVIQ3KEsptlA6N7wBuNx0t4HtHwAoi4mmv8Ad39mT0ps608y+GYm3j3TfFl+uL6eRbQbajfx6yzuumjLseVt7HumbWok7qRoL/eBIHoLzAdSB8p1ZxZXrfo8x9MYr2YV1qVsNWpuSwOZwRUTILe77qkc9p06nhKNJL09SVQk7k2Fxduepvv0nD+EVjQxOGrj6lRCf2SbP/23nb+IYCouX2LA0c9z7pchNygsfQ8vIGeTyz29Phvrjy30j4VTRo1lOZgzoxAt8S5gLHYe4fnPH1eF1MPU9lU3WxHQg6hh1UidE4tgGxGExKgC65XQW/SXpgkix1Ay3AvzJm04nwBMZh6JJCVRTTK9r6FRdSNLiTPk5HWs9rl1NLP42t5/+pdxHDx7Ks6j3ldL961RlB8nS335uhwhsNi6NPEKCjuFB3R1YgfK/PqJtKXDBSxrYWrf2eIpvTUnmP8AEQ36qVI8bdZ1dpMquxfBPZmjVIvTrIzEHYVadUtSP7uvis9dx2oxoVFyMcyEe7rod+/bpHAsK1PD0qbj3kFj4gnWZ9pl+vbvikUhkyEArlykW921rEW6TmL9k2yPU9nnWniKiAHUmiq1ELgcyGIJ7gbbTqQk0aYUAAWA5RnVianpyHsVgs+JoJVWyMlS2YWDrkba/wAQIvr3Tq/CsEaINMG9IEmnfdQd0PUA7eMs18DTGVWQGipBpnZqLA6ZWGoXp02222k3u/081nAyIvEgREQEREBERAREQhERAREQEwOLcLSvQq4dtFqKVuPqkjRh3g2Mz4geD7XdnMtBqqquUYXJWXmDRUNSqrffKVIPcZymphmFw6kNexB5HmPWfR2Kw61Eam4ujqVYdQRYicl4/wBnKpxQuCKlY53AsRT9o9Uomm5CqxNuYPdNcb/1OPPcGwFQ1sNlAGeqFRjsCoBJItuCQbTpfCMf7GotJFYUiTYOpD5QRnr1CBdSW91Rtry0l7s12fAwmE9oCtSnUGIF9GVmzHKehytYz0q4ZA5qBV9owALW94gbC/p6Tz+TXa3zOPLdmqXEKtSq+Mel7NTajkZHN898wKgWQrZcrXOnp63LbaUhBfNYZuttfWTmmTrrXca4SuIVAdGp1EqIeYKMD8xf5dJPGuHCqqsB+lpMKlM87qQSvgwFvObG8m8q9UrtFpOaIP0AStZSJUILWFia2dzTBsgt7SwJY3FwgttpYk9CBzNspcQveByJFh4d00/BxU/SXBN6tQ5gRcfpXGWx5ZVUTNpYqzmmysDYMb5SPeJABsdzlOms6l45uf8ArYRLCGzhR8JUkj7NiALdL3Ond4zJmk9srOKYlUSopiVRApiVSYFEREIREQEREBERAqE1WHpiqRWP28yHllUMieoLH78yeKVCKeVTZ3IRTzBbdh4KGPlKqagAAAAAWAGwA2E41WmM/wBVXi8iJm7LxEQpESRCIkybSDAm887j8XTqOhpV61QHNelSa6Ffte5Y3va1zY6+M9FPO4FglR6qKuWpcAaKqpTJysOVje/i4h1I2PDaRVSFHsxf4QBddBvfmTc+cykw4BJu12Nyb7mwH4ATCp1qhufcW9r3uTpptfp3+Utq9Ulr1GC7LZVB7ztty8u+T2742jLYFhfNbqTe17DX+tZlCa/CUzcKWZrAMxY69EHTkx8h1mxE1x8efyfSJMTpwiJMQIiTECiIiVCIiAiIgJIkSzjcWtGm9V75EUsbC50GwHMnYDvgYtQ56x+zSGX77i7ei5f3jMmY2CplUGb4zdn/AGmOZvS9vKZMxt7Xo5ycIiJAiJMA0xaNdqjMKdgqHKXIuCw3CjnbYnrpyMniFUqoC/GzKi9xY2v5C58pmYagEVUXZRYdfE953nUnXGrxjnDVeVRD3GmfxDym1b7FI+FQg+hT85nxadfmOf1Wnr4uoQ6CkwYC2bMpQFluDca6X6TGSiBlAXMygBQNwLAX6DYazbnXP3k/IAflNTw/GBQ5ewbOTYb2JIQeNh5a8pnW+PcZiYO/+IfujbzO5+UlsFYhqdhY6p9Q/wC0+Es+2qP8Og/VGYn7zaTKw71NnUeOgPmLmTrqqsBVzGpuCGFwRYgZVt5b6+MzJiUhmqE/YXLtqc9iT4e6PUzLm2fjy6+l4vIidIm8XkRAm8SIgIiIQiIgJbr1lRSzkBRuTLk5x9K3GGS1FWIGQGw6ux97yWmw+/EnRT2n+kQpdMPlzXtpYsP2mIIB7gPOeV7P9oK+Ix+H/tmIYUfagkM5FG6glARe184XfnaeVa4k7ibfifElfSjbyROQdj+3dTDAUsQGq0BYKw1q0x01+Je46jrynVeGcTo4hc9Cojr3HUdxXdT4zy6xct5rrLkSZE4dEmRF4Fmp71VF+zeoT00KKPPMx+5M8TS9neJ0qwZlb9IzE2OhKg2TL1AW23MnrN0JrJxjr6mIiVGGoOVxzzPa+2pJH4iYWHakNd353BzX58tJmVamWoFOzg28VsCPMEekr9kOgmOvr0YvphVOJU1+LOO/I5HqBpMhMUh1v6gjfxEqemgBLAWG99pGFwoHvZQOar9m/M9/4RIu9cXcIpOZiCMx0HMKL2v3m5PnMiQJM1jzW9IkxKIkxEBERAoiIlQiIgJwTt5xYYnG1WU/o1ORehCe7fzIJ852XtTxD2GFqPms2XKp53O5HeBc+U+eUcO7Hz7hc7TvE/qKlW/hKggH9ay4JM0RSJfwuJemwek7I42ZSQ3qJjkekkGOK6P2a+kY6U8cO4V1H/mgGniOuwnQKOJV1DIQykXDA3BHcZ88zbcD7QV8If0Le5zptrTPlyPeJjrxT+NM7/13P2khm0Nt7aTy/ZrtnQxRyEGlX+wxGVv2G+t4Gx7uc9ODMLLG0sce4ylfhWKa2ZsI7lk1IAub2VvqsNZ07sd2mp4ynYNeoo1vYMR1I5Hr685g9vXpDA1jVy/AwS9tXPwgA7m9j5X5TjHBuKVcLWSvRNnQg25MLWKnuIuPObZ/9Rjucr6XvF5r+B8Wp4uglekfccbc1I0ZT3g6TPhy13Gl0pt0qAeTgr+JHpKqOJIFm1I2PXx6GXOLJei1txZh90hvymJz85nqNvHfSqspYqag92+i8vE9T+EzsI10HmPQkflIamCLSMAtlyncFgfUn8CJMnk+MkRAkzRgREQpERAREQKIiJUIiUVqoRWZvhUFj4AXMDl30wcauVwtPUgXYDq1jr5W9WnOsNRCLbnz7zNjxnFmtXqVW+JnY/OYc2k5HJEhmA3ltXLbaDrKLspYSq0QKc1t/wCUrlJlkOD8N/HZf5wMibzB9tMfRXKlUOoFgKih2Xwbc+ZM0CE90nN3SWSrLxTxTitfEPnxFR3blmPur3Ko0UeAmJMuoob+tZivTK77dY4PZfRj2n/slf2NVv7vWYDuSobBX8DoD5HlO3z5dnbfox7T/wBqoewqt/eKIA13qU9lfvI2Pkecz1Fle1qLcEdQR6iabCvmCN1Cn1AM3c0eEFgo6Er+6xX8pjtt4m2lNLRyOoB8xoflllYluobMp77eTD+IWTK7+MkSZETRimJEQJiRECYkRApiIlQnne3nEPY4NzzYhR+J/C3nPRTlv0ycUs1LDqdQvtG+8xA/0zLn6Ods4G8svWPKWmMATZFSKSZlKLCRSSw75UTASipVA8ektNWJ+H1/reTTpf8AvmYRAud/Tl/OQzSqqw2EoRbmBkoNJMRCqXpgy0Qy94l+JRisgOq6HmP4TJ4NxSphqyV6Js6HbkR9ZG7iNJaqUuYllu/fr18ZzwfSHZ/jNPF0Fr0fhbcfWRh8SHvEsURv/wAyr/qvOMdhe1LYCvc3OHqWFVBv3OB9ofMeU7Nw+otRPaKQyO9VlYbMrVXKkeRE8/knGvjvts1OglGK+AnoM37vvflJoHSXCL6HY6TONNLgkzG4exNNc3xAWPipKn5iZM1jAiIgIiICIiBTERKhOJfS9/xD/wDGl+LyYlz9HijLmG+LykxN0ZMx8XsYiBQv5TJbbyiJBiGXaG8iIcsgREQ6IiJRDSw0RIMdOfiZ3H6Nf+H0vGr/AKjREw83xr4/r1dGXhETBrpZ4f8ACf26n+o0yoiax5yIiUIiICIiB//Z",
                    name = "North27",
                    nickName = "N27",
                    email = "b0972911675@north27.tw",
                    registerTime = "2021/02/22",
                    accessToken = "accessToken001",
                    refreshToken = "refreshToken001"
                )
            )
        )
    }

    var notifyList = mutableListOf<Pair<Int, MutableList<NotifyInfo>>>()
    override suspend fun getLoadNotify(@Field(value = "page") page: Int): List<NotifyInfo> {
        if (notifyList.isEmpty()) {
            notifyList.add(
                1 to mutableListOf(
                    NotifyInfo(
                        id = 0,
                        imgUrl = "https://cf.shopee.tw/file/b7b28075865ae8a751109478b6c59f2b_tn",
                        title = "噓！偷偷告訴你促銷期間輕鬆提升流量秘笈 v1.0.0",
                        desc = "大促期間流量&轉單大幅成長，商品下折扣卻沒有曝光嗎✨使用蝦皮關鍵字廣告讓你輕鬆獲得賣場流量，要曝光和業績就趁現在！",
                        time = "2021-02-26 16:31",
                        isRead = false,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 1,
                        imgUrl = "https://cf.shopee.tw/file/b3275b57f030fac1288e08a75f364017_tn",
                        title = "【重要】3/3(三)自動提款順延至3/10(三)執行 v1.0.1",
                        desc = "親愛的蝦皮用戶您好，由於內部作業系統維護，原定3/3(三)執行的自動提款將順延至3/10(三)執行，後續自動提款作業時間仍維持於3/17、3/31...以此類推，造成您的不便還請見諒\uD83D\uDE47未執行自動提領當週，仍有乙次免費提領機會，還請多加利用\uD83D\uDE4F",
                        time = "2021-02-26 14:33",
                        isRead = false,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 2,
                        imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                        title = "賣場佈置新組件上線！ v1.0.2",
                        desc = "賣場佈置全新組件讓您展示熱銷商品、新品以及促銷折扣，幫助您打造吸睛的賣場首頁，提升下單轉換率，點入了解更多\uD83D\uDC49",
                        time = "2021-02-23 14:49",
                        isRead = false,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 3,
                        imgUrl = "https://cf.shopee.tw/file/bcf02170cead12ea5d9319f8d4611823_tn",
                        title = "蝦皮動態模板懶人包送給你 v1.0.3",
                        desc = "【直播課程最便利－貼文牆引流量】在家就可以看\uD83D\uDD25到底怎麼經營粉絲，貼文牆怎麼玩？下半年最強功能之一你不能不會！貼文牆各種秘笈我們來教你\uD83D\uDE4C",
                        time = "2021-02-18 20:10",
                        isRead = true,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 4,
                        imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                        title = "新推出手機版【我的主題活動】 v1.0.4",
                        desc = "為了方便報名主題活動，您將可透過蝦皮APP來管理【我的主題活動】，同時也可即時查詢已報名成功的商品。更多相關說明請參考連結\uD83D\uDC49",
                        time = "2021-02-02 17:01",
                        isRead = true,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 5,
                        imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                        title = "防詐騙提醒 v1.0.5",
                        desc = "【重要提醒】蝦皮購物貼心提醒您，千萬不要透過LINE或其他通訊軟體約定交易、私下匯款賣家，也不要在商品未確認收到前聽信賣家指示提前點選〔完成訂單〕千萬注意，以免受騙上當哦！",
                        time = "2021-01-28 15:33",
                        isRead = false,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 6,
                        imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                        title = "賣家中心新功能上線：商品優化工具 v1.0.6",
                        desc = "親愛的賣家您好，賣家中心內的數據中心全新上線商品優化工具，透過商品優化工具可以幫助您找出需要優化的商品並提供調整方向與建議，確保賣場內所有商品內容都是高品質，吸引買家關注！立即點入了解更多\uD83D\uDC49",
                        time = "2021-01-27 17:00",
                        isRead = false,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 7,
                        imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                        title = "【重要提醒！強化帳戶安全性指南】 v1.0.7",
                        desc = "蝦皮絕不會要求您提供個人密碼、驗證碼，當您有以下狀況：無法登入帳號、有不明提款動作、發現非本人下單的訂單時，請盡速聯繫蝦皮客服團隊。點擊確認了解如何設定高強度密碼／帳戶有安全疑慮該怎麼辦\uD83D\uDC49",
                        time = "2021-01-25 11:03",
                        isRead = true,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 8,
                        imgUrl = "https://cf.shopee.tw/file/3cfd20ea4b19b1412fbea615813e6b0e_tn",
                        title = "邀請新朋友來蝦皮 v1.0.8",
                        desc = "新朋友註冊蝦皮後，在蝦皮購物App完成第1筆訂單，可享訂單金額 ",
                        time = "2021-01-11 18:56",
                        isRead = false,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 9,
                        imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                        title = "您的訂單已被取消！ v1.0.9",
                        desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
                        time = "2021-01-01 12:00",
                        isRead = false,
                        notifyType = NotifyType.NORMAL
                    )
                )
            )
            notifyList.add(
                2 to mutableListOf(
                    NotifyInfo(
                        id = 10,
                        imgUrl = "https://cf.shopee.tw/file/3cfd20ea4b19b1412fbea615813e6b0e_tn",
                        title = "邀請新朋友來蝦皮 v1.1.0",
                        desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
                        time = "2020-12-21 19:42",
                        isRead = false,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 11,
                        imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                        title = "您的訂單已被取消！ v1.1.1",
                        desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
                        time = "2020-12-21 12:00",
                        isRead = true,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 12,
                        imgUrl = "https://cf.shopee.tw/file/3cfd20ea4b19b1412fbea615813e6b0e_tn",
                        title = "邀請新朋友來蝦皮 v1.1.2",
                        desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
                        time = "2020-12-20 20:38",
                        isRead = false,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 13,
                        imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                        title = "您的訂單已被取消！ v1.1.3",
                        desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
                        time = "2020-12-18 12:00",
                        isRead = true,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 14,
                        imgUrl = "https://cf.shopee.tw/file/683d408bda45da45ca2f9414e19a16a0_tn",
                        title = "恭喜! 您已完成跨境實名認證 v1.1.4",
                        desc = "您已經成功地使用街口帳戶 +886972911675 完成了跨境商品實名認證流程。您不需再進行實名認證。請注意，您的跨境購物退款金額將被退回到這個街口帳戶。",
                        time = "2020-11-28 22:57",
                        isRead = false,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 15,
                        imgUrl = "https://cf.shopee.tw/file/2a19479ce918273f9afd2b7e86bb628d_tn",
                        title = "您的訂單已被取消！ v1.1.5",
                        desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
                        time = "2020-11-25 12:00",
                        isRead = false,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 16,
                        imgUrl = "https://cf.shopee.tw/file/79f99f9f49370fef139475db254e8c76_tn",
                        title = "別忘記你購物車內的商品！ v1.1.6",
                        desc = "Android TDD 測試驅動開發：從 UnitTest、TD... 還在你的購物車內，在商品完售前趕快購買！",
                        time = "2020-11-21 20:01",
                        isRead = true,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 17,
                        imgUrl = "https://cf.shopee.tw/file/9f70ed5d9a2f1105b5916c8c1309a721_tn",
                        title = "提醒您有蝦幣即將到期啦！ v1.1.7",
                        desc = "嗨，你的蝦幣即將在9/30, 23:59過期囉！趕緊進蝦皮，折抵蝦幣立即逛\uD83D\uDC49",
                        time = "2020-09-19 09:00",
                        isRead = true,
                        notifyType = NotifyType.NORMAL
                    ),
                    NotifyInfo(
                        id = 18,
                        imgUrl = "https://cf.shopee.tw/file/3cfd20ea4b19b1412fbea615813e6b0e_tn",
                        title = "邀請新朋友來蝦皮 v1.1.8",
                        desc = "嗨 aseijuy1995，很抱歉，您的訂單已被賣家取消。請點此查看更多相似的商品，祝您購物愉快！",
                        time = "2020-07-04 20:21",
                        isRead = false,
                        notifyType = NotifyType.NORMAL
                    )
                )
            )

        }
        delay(1500)
        return when {
            page > notifyList.last().first -> emptyList()
            else -> notifyList.first { page == it.first }.second
        }
    }

    override suspend fun postSwitchNotify(isOpen: Boolean): Boolean {
        delay(500)
        return isOpen
    }

    override suspend fun postReadAllNotify(): Boolean {
        delay(1000)
        return true
    }

    override suspend fun postDeleteNotify(notifyId: Long): Boolean {
//        notifyList.map { pair ->
//            val removeNotifys = pair.second.filter { it.id == notifyId }
//            pair.second.removeAll(removeNotifys)
//        }
        delay(500)
        return true
    }

}