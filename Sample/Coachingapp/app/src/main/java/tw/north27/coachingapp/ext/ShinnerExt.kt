package tw.north27.coachingapp.ext

import android.view.View
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout

fun ShimmerFrameLayout.startAlphaBreatheAnim() {
    val builder = Shimmer.AlphaHighlightBuilder()
    builder.setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
    setShimmer(builder.build())
    startShimmer()
    visibility = View.VISIBLE
}

fun ShimmerFrameLayout.stop() {
    stopShimmer()
    setShimmer(null)
    visibility = View.GONE
}