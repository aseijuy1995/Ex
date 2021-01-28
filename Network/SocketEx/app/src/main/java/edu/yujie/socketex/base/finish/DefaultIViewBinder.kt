package edu.yujie.socketex.base.finish

import android.database.Cursor
import android.view.View
import android.widget.TextView

class DefaultIViewBinder : IViewBinder {

    override fun setViewValue(view: View, cursor: Cursor, binding: Binding): Boolean {
        return if (view is TextView)
            setTextView(view, cursor, binding)
        else
            false
    }

    fun setTextView(tvView: TextView, cursor: Cursor, binding: Binding): Boolean {
        val columnIndex = binding.columnIndex
        val text = cursor.getString(columnIndex)
        tvView.text = text
        return true
    }

}