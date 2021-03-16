package tw.north27.coachingapp.util

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class TopScrollLinearLayoutManager(val cxt: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : LinearLayoutManager(cxt, attrs, defStyleAttr, defStyleRes) {

    override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
        super.smoothScrollToPosition(recyclerView, state, position)
        val scroller = TopLinearSmoothScroller(recyclerView!!.context)
        scroller.targetPosition = position
        startSmoothScroll(scroller)
    }

}

class TopLinearSmoothScroller(cxt: Context) : LinearSmoothScroller(cxt) {
    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START
    }

    override fun getHorizontalSnapPreference(): Int {
        return SNAP_TO_START
    }

//    override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int): Int {
//        return boxStart - viewStart
////        return super.calculateDtToFit(viewStart, viewEnd, boxStart, boxEnd, snapPreference)
//    }
}