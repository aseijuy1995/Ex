package edu.yujie.lohasapp

import edu.yujie.lohasapp.bean.AppResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IApiService {

    fun getApk()

    /**
     * 判斷版本
     * */
    @FormUrlEncoded
    @POST("/ct/api.php")
    suspend fun postVersion(@Field("cmd") cmd: String = "get_version_android"): AppResult

    /**
     * *取得會員資訊
     * */
    @FormUrlEncoded
    @POST("/ct/api.php")
    fun postMember(@Field("cmd") cmd: String = "get_mb_data", @Field("mb_no") mbNo: String? = null)

    /**
     * *登入
     * */
    @FormUrlEncoded
    @POST("/ct/api.php")
    fun postSignIn(@Field("cmd") cmd: String = "get_login", @Field("from") from: String = "FROMS", @Field("login_id") loginId: String, @Field("login_pwd") loginPwd: String)


    //-------------------------------------------------------------------------------------------------------

    /**
     * 撈取分類數據
     * */
    @FormUrlEncoded
    @POST("/ct/api.php")
    fun postKind(@Field("cmd") cmd: String = "get_all_data", @Field("data_kind") dataKind: String)

    /**
     * 搜尋紀錄
     * */
    @FormUrlEncoded
    @POST("/ct/api.php")
    fun postKind(@Field("cmd") cmd: String = "get_mb_his_keyword", @Field("data_kind") dataKind: String, @Field("mb_no") mbNo: String? = null)

    /**
     * 熱搜
     * */
    @FormUrlEncoded
    @POST("/ct/api.php")
    fun postPopularProduct(@Field("cmd") cmd: String = "get_public_keyword", @Field("data_kind") dataKind: String)

    /**
     * 清空搜尋紀錄
     * */
    @FormUrlEncoded
    @POST("/ct/api.php")
    fun postClearHistory(@Field("cmd") cmd: String = "clear_mb_his_keyword", @Field("data_kind") dataKind: String, @Field("mb_no") mbNo: String? = null)

    /**
     * 存取搜尋紀錄
     * */
    @FormUrlEncoded
    @POST("/ct/api.php")
    fun postSaveHistory(@Field("cmd") cmd: String = "put_search_keyword", @Field("data_kind") dataKind: String, @Field("mb_no") mbNo: String? = null, @Field("keyword") keyWord: String)

    /**
     * 顯示購物車數量
     * */
    @FormUrlEncoded
    @POST("/ct/api.php")
    fun postCartCount(@Field("cmd") cmd: String = "get_cart_prod_list", @Field("mb_no") mbNo: String? = null)

    //-------------------------------------------------------------------------------------------------------

    /**
     * 廣告:57
     * banner:60
     * */
    @FormUrlEncoded
    @POST("/ct/api.php")
    suspend fun postImg(@Field("cmd") cmd: String = "get_img", @Field("kind") kind: String = "57"): List<AdvertBean>

    /**
     * 新訓
     * */
    @FormUrlEncoded
    @POST("/ct/api.php")
    fun postMsg(@Field("cmd") cmd: String = "get_home_new")

    /**
     * 樂果精選:featured = 1,home_show = 1
     * 樂果精選不足10筆:featured = 0, sort = sort
     * 商城熱門:
     * 商城熱門不足10筆:
     * */
    @FormUrlEncoded
    @POST("/ct/api.php")
    fun postProduct(
        @Field("cmd") cmd: String = "get_prod_list",
        @Field("featured") featured: String? = null,
        @Field("mb_no") mbNo: String? = null,
        @Field("home_show") homeShow: String = "1",
        @Field("limit_num") limitNum: String = "10",
        @Field("sort") sort: String? = null
    )


}