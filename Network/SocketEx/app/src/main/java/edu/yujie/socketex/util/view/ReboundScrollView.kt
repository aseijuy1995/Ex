package edu.yujie.socketex.util.view

import android.R.attr
import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView


class ReboundScrollView(context: Context, attrs: AttributeSet) : ScrollView(context, attrs) {
    private val MAX_SCROLL = 200
    private val SCROLL_RATIO = 0.5f

    override fun overScrollBy(deltaX: Int, deltaY: Int, scrollX: Int, scrollY: Int, scrollRangeX: Int, scrollRangeY: Int, maxOverScrollX: Int, maxOverScrollY: Int, isTouchEvent: Boolean): Boolean {
        var newDeltaY = deltaY
        val delta = (deltaY * SCROLL_RATIO) as Int
        newDeltaY = if (attr.scrollY + deltaY === 0 || attr.scrollY - scrollRangeY + deltaY === 0) {
            deltaY
        } else {
            delta
        }
        return super.overScrollBy(deltaX, newDeltaY, attr.scrollX, attr.scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, MAX_SCROLL, isTouchEvent)
    }
}