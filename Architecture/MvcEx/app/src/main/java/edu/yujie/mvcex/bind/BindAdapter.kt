package edu.yujie.mvcex.bind

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import edu.yujie.mvcex.R

@BindingAdapter("app:imgUrl")
fun ImageView.imgUrl(url:String?){
    Glide.with(context).load(url).placeholder(R.drawable.ic_icon_black).into(this)
}