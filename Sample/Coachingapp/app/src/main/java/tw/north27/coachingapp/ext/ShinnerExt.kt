package tw.north27.coachingapp.ext

import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout

fun ShimmerFrameLayout.start() {
    startShimmer()
    visibility = View.VISIBLE
}

fun ShimmerFrameLayout.stop() {
    stopShimmer()
    setShimmer(null)
    visibility = View.GONE
}