package edu.yujie.socketex.finish.bean

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import edu.yujie.socketex.UserBean
import java.io.InputStream
import java.io.OutputStream

object UserSerializer : Serializer<UserBean> {
    override fun readFrom(input: InputStream): UserBean {
        try {
            return UserBean.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override fun writeTo(t: UserBean, output: OutputStream) = t.writeTo(output)
}

//data class User(
//    val id: Int,
//    val account: String,
//    val password: String,
//    val authority: String,//權限 學生 小幫手 志工 家長 老師
//    val headerUrl: String,//頭貼
//    val name: String,//名稱
//    val lastName: String,//姓氏
//    val userName: String,//使用者名稱
//    val gender: String,//性別
//    val birthday: String,//生日
//    val school: String,//學校
//    val grade: String,//年級
//    val phone: String,//手機
//    val email: String//email
//)