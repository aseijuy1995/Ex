package tw.north27.coachingapp.ext2

import android.view.View
import android.view.animation.*

/**
 * alpha：呼吸效果
 * */
fun View.startAlphaBreatheAnim() {
    val alphaAnim = createAlphaAnim(AlphaSetting.EFFECT_DEEPER_INFINITE_REVERSE)
    startAnimation(alphaAnim)
}

/**
 * alpha & scale：水波紋效果
 * */
fun View.startAlphaScaleWaterRippleAnim() {
    val animationSet = AnimationSet(true)
    val alphaAnim = createAlphaAnim(AlphaSetting.EFFECT_FADE_INFINITE_RESTART)
    val scaleAnim = createScaleAnim(ScaleSetting.EFFECT_CENTER_EXPANSION_INFINITE)
    animationSet.addAnimation(alphaAnim)
    animationSet.addAnimation(scaleAnim)
    animationSet.interpolator = DecelerateInterpolator()
    animationSet.fillAfter = false
    animationSet.duration = 1000
    startAnimation(animationSet)
}

/**
 * 設定類：透明動畫
 * */
data class AlphaSetting(
    val fromAlpha: Float,
    val toAlpha: Float,
    val duration: Long,
    val repeatCount: Int,
    val repeatMode: Int,
) {

    companion object {
        /**
         * 漸深重複逆轉效果
         * */
        val EFFECT_DEEPER_INFINITE_REVERSE = AlphaSetting(
            fromAlpha = 0.0F,
            toAlpha = 1.0F,
            duration = 1000,
            repeatCount = Animation.INFINITE,
            repeatMode = Animation.REVERSE
        )


        /**
         * 漸淺重複順轉效果
         * */
        val EFFECT_FADE_INFINITE_RESTART = AlphaSetting(
            fromAlpha = 1.0F,
            toAlpha = 0.0F,
            duration = 1000,
            repeatCount = Animation.INFINITE,
            repeatMode = Animation.RESTART
        )
    }
}

/**
 * 設置透明動畫
 * */
fun createAlphaAnim(setting: AlphaSetting): AlphaAnimation {
    return AlphaAnimation(
        setting.fromAlpha,
        setting.toAlpha
    ).apply {
        this.duration = setting.duration
        repeatCount = setting.repeatCount
        repeatMode = setting.repeatMode
    }
}


/**
 * 設定類：比例動畫
 * */
data class ScaleSetting(
    val fromX: Float,
    val toX: Float,
    val fromY: Float,
    val toY: Float,
    val pivotXType: Int,
    val pivotXValue: Float,
    val pivotYType: Int,
    val pivotYValue: Float,
    val repeatCount: Int,
    val duration: Long,
) {

    companion object {
        /**
         * 中心向外擴展無限效果
         * */
        val EFFECT_CENTER_EXPANSION_INFINITE = ScaleSetting(
            fromX = 1F,
            toX = 1.3F,
            fromY = 1F,
            toY = 1.3F,
            pivotXType = ScaleAnimation.RELATIVE_TO_SELF,
            pivotXValue = 0.5F,
            pivotYType = ScaleAnimation.RELATIVE_TO_SELF,
            pivotYValue = 0.5F,
            repeatCount = Animation.INFINITE,
            duration = 1000,
        )
    }
}

/**
 * 設置比例動畫
 * */
fun createScaleAnim(setting: ScaleSetting): ScaleAnimation {
    return ScaleAnimation(
        setting.fromX,
        setting.toX,
        setting.fromY,
        setting.toY,
        setting.pivotXType,
        setting.pivotXValue,
        setting.pivotYType,
        setting.pivotYValue
    ).apply {
        repeatCount = setting.repeatCount
        duration = setting.duration
    }
}