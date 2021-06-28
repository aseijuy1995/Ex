package tw.north27.coachingapp.adapter

import android.graphics.Color
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.Gender
import tw.north27.coachingapp.model.UserInfo
import java.text.SimpleDateFormat
import java.util.*

/**
 * 設置性別UI
 * @param userInfo >> 用戶資訊
 * */
@BindingAdapter("bind:gender")
fun TextView.bindGender(userInfo: UserInfo?) {
    @DrawableRes val imgRes: Int
    @StringRes val strRes: Int
    when (userInfo?.gender) {
        Gender.MALE -> {
            imgRes = R.drawable.shape_solid_blue_corners_radius_2
            strRes = R.string.male
        }
        Gender.FEMALE -> {
            imgRes = R.drawable.shape_solid_red_corners_radius_2
            strRes = R.string.female
        }
        else -> {
            imgRes = R.drawable.shape_solid_green_corners_radius_2
            strRes = R.string.not
        }
    }
    setBackgroundResource(imgRes)
    text = context.getString(strRes)
}

/**
 * 設置評論UI
 * @param userInfo >> 用戶資訊
 * */
//@BindingAdapter("bind:chartComment")
fun PieChart.bindChartComment(
    userInfo: UserInfo?,
    valueTextSize: Float = 10f,
    isDrawEntryLabels: Boolean = false,
    holeRadius: Float = 60f,
    centerTextSize: Float = 14f,
    isLegend: Boolean = true
) {
    isVisible = (userInfo?.teacherInfo?.commentScoreCountList != null) && userInfo.teacherInfo.commentScoreCountList.isNotEmpty()
    val pieEntryList = mutableListOf<PieEntry>()
    userInfo?.teacherInfo?.commentScoreCountList?.forEach {
        pieEntryList.add(PieEntry(it.count.toFloat(), "${it.score} ${context.getString(R.string.star)}：${it.count}${context.getString(R.string.pen)}"))
    }
    val pieDataSet = PieDataSet(pieEntryList, null)
    pieDataSet.apply {
        sliceSpace = 2f
        selectionShift = 5f
        colors = arrayListOf<Int>(
            ContextCompat.getColor(context, R.color.red_e50014),
            ContextCompat.getColor(context, R.color.orange_f09401),
            ContextCompat.getColor(context, R.color.yellow_f7cd3b),
            ContextCompat.getColor(context, R.color.green_00ba9b),
            ContextCompat.getColor(context, R.color.blue_02abe2)
        )
    }
    val pieData = PieData(pieDataSet)
    pieData.apply {
        setDrawValues(true)
        setValueFormatter(PercentFormatter(this@bindChartComment))
        setValueTextColor(Color.WHITE)
        setValueTextSize(valueTextSize)
    }
    setUsePercentValues(true)
    description.isEnabled = false
    rotationAngle = 90f
    isHighlightPerTapEnabled = true
    animateY(1500, Easing.EaseInQuart)
    setDrawEntryLabels(isDrawEntryLabels)
    setEntryLabelColor(Color.WHITE)
    setEntryLabelTextSize(8f)
    this.holeRadius = holeRadius
    centerText = "${context.getString(R.string.score)}\n${userInfo?.teacherInfo?.commentScoreAvg}"
    setCenterTextSize(centerTextSize)
    legend.apply {
        isEnabled = isLegend
        verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        orientation = Legend.LegendOrientation.VERTICAL
        setDrawInside(false)
        xOffset = 0f
        form = Legend.LegendForm.CIRCLE
    }
    data = pieData
    invalidate()
}


/**
 * 設置回覆率UI
 * @param userInfo >> 用戶資訊
 * */
//@BindingAdapter("bind:chartReply")
fun PieChart.bindChartReply(
    userInfo: UserInfo?,
    valueTextSize: Float = 10f,
    isDrawEntryLabels: Boolean = false,
    holeRadius: Float = 60f,
    centerTextSize: Float = 14f,
    isLegend: Boolean = true
) {
    isVisible = (userInfo?.teacherInfo?.replyCountList != null) && userInfo.teacherInfo.replyCountList.isNotEmpty()
    val pieEntryList = mutableListOf<PieEntry>()
    userInfo?.teacherInfo?.replyCountList?.forEach {
        pieEntryList.add(PieEntry(it.count.toFloat(), "${it.reply}：${it.count}${context.getString(R.string.pen)}"))
    }
    val pieDataSet = PieDataSet(pieEntryList, null)
    pieDataSet.apply {
        sliceSpace = 2f
        selectionShift = 5f
        colors = arrayListOf<Int>(
            ContextCompat.getColor(context, R.color.blue_082e81),
            ContextCompat.getColor(context, R.color.purple_a672df)
        )
    }
    val pieData = PieData(pieDataSet)
    pieData.apply {
        setDrawValues(true)
        setValueFormatter(PercentFormatter(this@bindChartReply))
        setValueTextColor(Color.WHITE)
        setValueTextSize(valueTextSize)
    }
    setUsePercentValues(true)
    description.isEnabled = false
    rotationAngle = 90f
    isHighlightPerTapEnabled = true
    animateY(1500, Easing.EaseInQuart)
    setDrawEntryLabels(isDrawEntryLabels)
    setEntryLabelColor(Color.WHITE)
    setEntryLabelTextSize(8f)
    this.holeRadius = holeRadius
    centerText = "${context.getString(R.string.reply_rate)}\n${userInfo?.teacherInfo?.replyRate}%"
    setCenterTextSize(centerTextSize)
    legend.apply {
        isEnabled = isLegend
        verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        orientation = Legend.LegendOrientation.VERTICAL
        setDrawInside(false)
        xOffset = 0f
        form = Legend.LegendForm.CIRCLE
    }
    data = pieData
    invalidate()
}


/**
 * 日期轉換
 * @param date >> 日期
 * */
@BindingAdapter("bind:dateFormat")
fun TextView.bindDate(date: Date?) {
    text = SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN).format(date)
}