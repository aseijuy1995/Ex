package edu.yujie.socketex.finish.inter

enum class SignInStatus {
    NOT_SIGN_IN, //未登入
    EXPIRED_TOKEN, //過期
    ERROR_ACT_PWD, //帳密錯誤
    SIGN_IN //已登入
}