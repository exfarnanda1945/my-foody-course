package com.exfarnanda1945.my_foody_course.binding_adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.exfarnanda1945.my_foody_course.R
import org.jsoup.Jsoup

object OverviewBindingAdapter {

    @BindingAdapter("loadOverviewImage")
    @JvmStatic
    fun loadOverviewImage(imgView: ImageView, url: String) {
        val placeHolderDrawable = R.drawable.default_placeholder
        Glide.with(imgView.context).load(url).placeholder(placeHolderDrawable)
            .error(placeHolderDrawable)
            .into(imgView)
    }

    @BindingAdapter("setOverviewLikes")
    @JvmStatic
    fun setOverviewLikes(tv: TextView, value: Int) {
        tv.text = value.toString()
    }

    @BindingAdapter("setOverviewMinutes")
    @JvmStatic
    fun setOverviewMinutes(tv: TextView, value: Int) {
        tv.text = value.toString()
    }

    @BindingAdapter("isVegan")
    @JvmStatic
    fun isVegan(view: View, isActive: Boolean) {
        changeColorIsChecked(view, isActive)
    }

    @BindingAdapter("isVegetarian")
    @JvmStatic
    fun isVegetarian(view: View, isActive: Boolean) {
        changeColorIsChecked(view, isActive)
    }

    @BindingAdapter("isGlutenFree")
    @JvmStatic
    fun isGlutenFree(view: View, isActive: Boolean) {
        changeColorIsChecked(view, isActive)
    }

    @BindingAdapter("isDiaryFree")
    @JvmStatic
    fun isDiaryFree(view: View, isActive: Boolean) {
        changeColorIsChecked(view, isActive)
    }

    @BindingAdapter("isHealthy")
    @JvmStatic
    fun isHealthy(view: View, isActive: Boolean) {
        changeColorIsChecked(view, isActive)
    }

    @BindingAdapter("isCheap")
    @JvmStatic
    fun isCheap(view: View, isActive: Boolean) {
        changeColorIsChecked(view, isActive)
    }

    @BindingAdapter("parseHtml")
    @JvmStatic
    fun parseHtml(textView: TextView,description:String?){
        if(description!=null){
            val desc = Jsoup.parse(description).text()
            textView.text = desc
        }
    }


    private fun changeColorIsChecked(view: View, isActive: Boolean) {
        val context = view.context
        val colorGreen = ContextCompat.getColor(context, R.color.green)

        if (isActive) {
            when (view) {
                is TextView -> {
                    view.setTextColor(colorGreen)
                }
                is ImageView -> {
                    view.setColorFilter(
                        colorGreen
                    )
                }
            }
        }
    }
}