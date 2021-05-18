package tw.north27.coachingapp.util

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.navigation.Navigation.findNavController

import tw.north27.coachingapp.R
import tw.north27.coachingapp.ext.versionCode
import tw.north27.coachingapp.ext.versionName
import tw.north27.coachingapp.ui.StartFragmentDirections

/**
 * 版本更新
 *
 * 支持versionName & versionCode
 * 使用versionName作為判斷依據時可傳遞判斷模式（VersionNameMode）
 * */
class UpdateApp<T> constructor(
    private val act: Activity,
    private val build: UpdateApp.Builder<T>
) {

    companion object {
        fun with(act: Activity, versionName: String): Builder<String> {
            return Builder(act = act, versionName = versionName)
        }

        fun with(act: Activity, versionCode: Long): Builder<Long> {
            return Builder(act = act, versionCode = versionCode)
        }
    }

    class Builder<T> constructor(
        private val act: Activity,
        val versionName: String? = null,
        val versionCode: Long? = null
    ) {
        var versionNameMode: VersionNameMode = VersionNameMode.DEFAULT

        //        var isUpdateDialog: Boolean = true
        var drawableResId: Int = R.drawable.ic_background_update
        var title: String = act.getString(R.string.update_title)
        var text: String = act.getString(R.string.update_text)
        var url: String? = null
        var size: String? = null
        var isMandatory: Boolean = true

        var viewId: Int? = null

        fun builder(builder: UpdateApp.Builder<T>.() -> Unit): UpdateApp<T> {
            builder.invoke(this)
            return UpdateApp(act, this)
        }
    }

    fun execute(
        newVersion: ((currentVersion: T, targetVersion: T) -> Unit)? = null,
        noNewVersion: ((currentVersion: T) -> Unit)? = null
    ) {
        if (build.versionName != null) {
            when (build.versionNameMode) {
                VersionNameMode.FRONT -> {
                    if (compareFront(build.versionName)) {
                        newVersion?.invoke(act.versionName as T, build.versionName as T)
                        build.viewId?.let { findNavController(act, it).navigate(StartFragmentDirections.actionFragmentStartToFragmentUpdateDialog()) }
                    } else noNewVersion?.invoke(build.versionName as T)
                }
                VersionNameMode.MIDDLE -> {
                    if (compareMiddle(build.versionName)) {
                        newVersion?.invoke(act.versionName as T, build.versionName as T)
                        build.viewId?.let { findNavController(act, it).navigate(StartFragmentDirections.actionFragmentStartToFragmentUpdateDialog()) }
                    } else noNewVersion?.invoke(build.versionName as T)
                }
                VersionNameMode.DEFAULT -> {
                    if (compareDefault(build.versionName)) {
                        newVersion?.invoke(act.versionName as T, build.versionName as T)
                        build.viewId?.let { findNavController(act, it).navigate(StartFragmentDirections.actionFragmentStartToFragmentUpdateDialog()) }
                    } else noNewVersion?.invoke(build.versionName as T)
                }
            }
        } else if (build.versionCode != null) {
            if (build.versionCode.toLong() > act.versionCode) {
                newVersion?.invoke(act.versionCode as T, build.versionCode.toLong() as T)
                build.viewId?.let { findNavController(act, it).navigate(StartFragmentDirections.actionFragmentStartToFragmentUpdateDialog()) }
            } else
                noNewVersion?.invoke(build.versionCode.toLong() as T)
        } else {
            throw Exception("Please Enter versionName or versionCode!")
        }
    }

    private fun compareFront(versionName: String): Boolean {
        val pair = splitVersionName(versionName)
        return pair.first[0] < pair.second[0]
    }

    private fun compareMiddle(versionName: String): Boolean {
        val pair = splitVersionName(versionName)
        return if (pair.first[0] < pair.second[0]) true
        else (pair.first[0] == pair.second[0] && pair.first[1] < pair.second[1])
    }

    private fun compareDefault(versionName: String): Boolean {
        val pair = splitVersionName(versionName)
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
    private fun splitVersionName(versionName: String): Pair<List<Int>, List<Int>> =
        act.versionName.split(".").map { it.toInt() } to versionName.split(".").map { it.toInt() }


    /**
     * FRONT >> 前段
     * MIDDLE >> 中段
     * DEFAULT >> 後段（預設）
     * */
    enum class VersionNameMode {
        FRONT, MIDDLE, DEFAULT
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





