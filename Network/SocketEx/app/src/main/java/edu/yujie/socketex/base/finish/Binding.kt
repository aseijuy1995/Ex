package edu.yujie.socketex.base.finish

import android.database.Cursor

class Binding(val type: Int, val viewId: Int, val columnName: String?) {
    var columnIndex = -1
        private set

    constructor(viewId: Int, columnName: String?) : this(0, viewId, columnName) {}

    fun isType(type: Int): Boolean {
        return this.type == type
    }

    fun findColumnIndex(cursor: Cursor) {
        if (columnIndex < 0) {
            columnIndex = cursor.getColumnIndexOrThrow(columnName)
        }
    }
}