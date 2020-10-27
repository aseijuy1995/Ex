package edu.yujie.viewmodelex

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * @author YuJie on 2020/10/27
 * @describe 說明
 * @param 參數
 */
class SavedStateViewModel(private val state: SavedStateHandle) : ViewModel() {

    private val USERID_KEY = "userId"

    fun setUserState() {
        state.set(USERID_KEY, "userId = 123")
    }

    fun getUserState(): String? = state.get<String>(USERID_KEY)

}