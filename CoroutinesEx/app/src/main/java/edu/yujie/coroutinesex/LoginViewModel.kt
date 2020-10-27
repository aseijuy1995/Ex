package edu.yujie.coroutinesex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */
class LoginViewModel(val repo: LoginRepository) : ViewModel() {

    fun login(userName: String, token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val jsonBody = "{ userName: \"$userName\", token: \"$token\"}"
            val result = repo.makeLoginRequest(jsonBody)
            when (result) {
                "Success" -> {

                }
                "Error" -> {

                }
            }
        }
    }

}