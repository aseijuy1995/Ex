package edu.yujie.socketex.base.finish

import android.database.Cursor
import android.view.View
import edu.yujie.socketex.base.finish.Binding


interface IViewBinder {

    fun setViewValue(view: View, cursor: Cursor, binding: Binding): Boolean

}

