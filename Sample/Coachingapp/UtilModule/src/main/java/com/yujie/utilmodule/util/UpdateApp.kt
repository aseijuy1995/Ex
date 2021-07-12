package com.yujie.utilmodule.util

import android.app.Activity
import androidx.annotation.DrawableRes

/**
 * 版本更新
 *
 * 支持versionName & versionCode
 * 使用versionName作為判斷依據時可傳遞判斷模式（VersionNameMode）
 * */
class UpdateApp<T> constructor(private val build: Builder<T>) {

    companion object {
        fun with(act: Activity, newVersionName: String, versionName: String): Builder<String> {
            return Builder(act = act, newVersionName = newVersionName, versionName = versionName)
        }

        fun with(act: Activity, newVersionCode: Long, versionCode: Long): Builder<Long> {
            return Builder(act = act, newVersionCode = newVersionCode, versionCode = versionCode)
        }
    }

    class Builder<T> constructor(
        private val act: Activity,
        val newVersionName: String? = null,
        val newVersionCode: Long? = null,
        val versionName: String? = null,
        val versionCode: Long? = null,
    ) {
        var versionNameMode: VersionNameMode = VersionNameMode.DEFAULT

        fun builder(builder: Builder<T>.() -> Unit): UpdateApp<T> {
            builder.invoke(this)
            return UpdateApp(this)
        }
    }

    fun execute(
        newVersion: ((current: T, target: T) -> Unit)? = null,
        noNewVersion: ((currentVersion: T) -> Unit)? = null
    ) {
        if (build.newVersionName != null && build.versionName != null) {
            when (build.versionNameMode) {
                VersionNameMode.FRONT -> {
                    if (compareFront(build.newVersionName)) newVersion?.invoke(build.versionName as T, build.newVersionName as T)
                    else noNewVersion?.invoke(build.newVersionName as T)
                }

                VersionNameMode.MIDDLE -> {
                    if (compareMiddle(build.newVersionName)) newVersion?.invoke(build.versionName as T, build.newVersionName as T)
                    else noNewVersion?.invoke(build.newVersionName as T)
                }

                VersionNameMode.DEFAULT -> {
                    if (compareDefault(build.newVersionName)) newVersion?.invoke(build.versionName as T, build.newVersionName as T)
                    else noNewVersion?.invoke(build.newVersionName as T)
                }
            }
        } else if (build.newVersionCode != null && build.versionCode != null) {
            if (build.newVersionCode.toLong() > build.versionCode) newVersion?.invoke(build.versionCode as T, build.newVersionCode.toLong() as T)
            else noNewVersion?.invoke(build.newVersionCode.toLong() as T)
        } else {
            throw Exception("Please Enter versionName or versionCode!")
        }
    }

    private fun compareFront(newVersionName: String): Boolean {
        val pair = splitVersionName(newVersionName)
        return pair.first[0] < pair.second[0]
    }

    private fun compareMiddle(newVersionName: String): Boolean {
        val pair = splitVersionName(newVersionName)
        return if (pair.first[0] < pair.second[0]) true
        else (pair.first[0] == pair.second[0] && pair.first[1] < pair.second[1])
    }

    private fun compareDefault(newVersionName: String): Boolean {
        val pair = splitVersionName(newVersionName)
        return if (pair.first[0] < pair.second[0]) true
        else if (pair.first[0] == pair.second[0] && pair.first[1] < pair.second[1]) true
        else (pair.first[0] == pair.second[0]) && (pair.first[1] == pair.second[1]) && (pair.first[2] < pair.second[2])
    }

    /**
     * @return Pair<first,second>
     *
     * first >> 當前版本名
     * second >> 目標版本名
     * */
    private fun splitVersionName(newVersionName: String): Pair<List<Int>, List<Int>> =
        build.versionName!!.split(".").map { it.toInt() } to newVersionName.split(".").map { it.toInt() }

    /**
     * FRONT >> 前段
     * MIDDLE >> 中段
     * DEFAULT >> 後段（預設）
     * */
    enum class VersionNameMode() {
        FRONT,
        MIDDLE,
        DEFAULT;
    }

    data class Update(
        @DrawableRes val drawableResId: Int,
        val versionName: String?,
        val versionCode: Long?,
        val url: String,
        val text: String,
        val size: String,
        val isMandatory: Boolean
    )
}





