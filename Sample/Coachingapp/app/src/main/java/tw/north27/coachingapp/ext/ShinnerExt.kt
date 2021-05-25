package tw.north27.coachingapp.ext

import android.view.View
import androidx.core.view.isVisible
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout

var ShimmerFrameLayout.isVisible: Boolean
    set(value) {
        if (value)
            start()
        else
            stop()
    }
    get() = isVisible

fun ShimmerFrameLayout.start() {
    val builder = Shimmer.AlphaHighlightBuilder()
    setShimmer(builder.apply {
        setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        setTilt(0f)
        setDuration(1500)
    }.build())
    startShimmer()
    visibility = View.VISIBLE
}

fun ShimmerFrameLayout.stop() {
    stopShimmer()
    setShimmer(null)
    visibility = View.GONE
}