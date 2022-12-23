package com.exfarnanda1945.my_foody_course.binding_adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.exfarnanda1945.my_foody_course.R
import com.exfarnanda1945.my_foody_course.util.Constants.BASE_IMAGE_URL
import com.exfarnanda1945.my_foody_course.util.capitalized

object IngredientRowBindingAdapter {
    @BindingAdapter("loadIngredientImage")
    @JvmStatic
    fun loadIngredientImage(imgView: ImageView, fileName: String?) {
        val placeHolderDrawable = R.drawable.default_placeholder
        val url = "${BASE_IMAGE_URL}${fileName}"
        Glide.with(imgView.context).load(url).placeholder(placeHolderDrawable)
            .error(placeHolderDrawable)
            .into(imgView)
    }

    @BindingAdapter("setIngredientName")
    @JvmStatic
    fun setIngredientName(tv: TextView, value: String) {
        tv.text = value.capitalized()
    }
}