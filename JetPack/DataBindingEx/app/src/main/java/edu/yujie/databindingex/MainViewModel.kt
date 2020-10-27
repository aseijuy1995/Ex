package edu.yujie.databindingex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */
class MainViewModel : ViewModel() {
    private val mUser = MutableLiveData(User("Test", "User", true))
    private val mList = MutableLiveData(listOf(1, 2, 3))
    private val mHandlers = MutableLiveData(MyHandlers())
    private val mBindingAdapterOnToastClickStr = MutableLiveData("BindingAdapter")
    private val mIsError = MutableLiveData(false)
    private val mNumber = MutableLiveData(3)

    val user: LiveData<User> = mUser
    val list: LiveData<List<Int>> = mList
    val handlers: LiveData<MyHandlers> = mHandlers
    val bindingAdapterOnToastClickStr: LiveData<String> = mBindingAdapterOnToastClickStr
    val isError: LiveData<Boolean> = mIsError
    val number: LiveData<Int> = mNumber
}