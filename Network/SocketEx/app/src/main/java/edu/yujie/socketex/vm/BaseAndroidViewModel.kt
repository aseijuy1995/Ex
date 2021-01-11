package edu.yujie.socketex.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel

open class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    val TAG = javaClass.simpleName

    val context = application.applicationContext

}