package com.exfarnanda1945.my_foody_course.binding_adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.exfarnanda1945.my_foody_course.R
import com.exfarnanda1945.my_foody_course.model.ResultsItem
import com.exfarnanda1945.my_foody_course.ui.fragments.recipes.RecipesFragmentDirections

object RecipeRowLayoutBindingAdapter {

    @BindingAdapter("onRecipeClickListener")
    @JvmStatic
    fun onRecipeClickListener(layout: ConstraintLayout, result: ResultsItem) {
        try {
            layout.setOnClickListener {
                val action =
                    RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
                layout.findNavController().navigate(action)
            }
        } catch (e: Exception) {
            Log.d("BindingAdapter", "onRecipeClickListener: $e")
        }
    }

    @BindingAdapter("loadImageFromUrl")
    @JvmStatic
    fun loadImageFromUrl(imageView: ImageView, imgUrl: String) {
        val placeHolderDrawable = R.drawable.default_placeholder
        Glide.with(imageView.context).load(imgUrl).placeholder(placeHolderDrawable)
            .error(placeHolderDrawable)
            .into(imageView)
    }

    /*
    * JvmStatic --> Tell the compiler to generate method(function) or setter and getter(property) so in xml layout we can access it
    * BindingAdapter --> Method for manipulate values with expression set into views. Because data binding only can direct string value.
    *                    if want to set value with expression such Int,Boolean, you need to create binding adapter
    * Function binding adapter must have a view parameter. But in xml layout when called the function binding adapter,you only set a value from function
    * */
    @BindingAdapter("setNumberOfLikes")
    @JvmStatic
    fun setNumberOfLikes(tv: TextView, likes: Int) {
        tv.text = likes.toString()
    }

    @BindingAdapter("setNumberOfMinutes")
    @JvmStatic
    fun setNumberOfMinutes(tv: TextView, minutes: Int) {
        tv.text = minutes.toString()
    }

    // Because image and string parented by view, we need a parameter View for accessing image and string.
    @BindingAdapter("applyIsVegan")
    @JvmStatic
    fun applyIsVegan(view: View, isVegan: Boolean) {
        val context = view.context
        val colorGreen = ContextCompat.getColor(context, R.color.green)
        if (isVegan) {
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