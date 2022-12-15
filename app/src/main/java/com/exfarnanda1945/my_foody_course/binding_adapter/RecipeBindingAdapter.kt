package com.exfarnanda1945.my_foody_course.binding_adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.exfarnanda1945.my_foody_course.data.database.RecipesEntity
import com.exfarnanda1945.my_foody_course.model.FoodRecipe
import com.exfarnanda1945.my_foody_course.util.NetworkResult

object RecipeBindingAdapter {

    @BindingAdapter("readApiResponse", "readDatabaseResponse", requireAll = true)
    @JvmStatic
    fun errorImageViewVisibility(
        imageView: ImageView,
        apiResponse: NetworkResult<FoodRecipe>?,
        databaseResponse: List<RecipesEntity>?
    ) {
        if (apiResponse is NetworkResult.Error && databaseResponse.isNullOrEmpty()) {
            imageView.isVisible = true
        } else if (apiResponse is NetworkResult.Loading || apiResponse is NetworkResult.Success) {
            imageView.isVisible = false
        }
    }

    @BindingAdapter("readApiResponse", "readDatabaseResponse", requireAll = true)
    @JvmStatic
    fun errorTextViewVisibility(
        textView: TextView,
        apiResponse: NetworkResult<FoodRecipe>?,
        databaseResponse: List<RecipesEntity>?
    ) {
        if (apiResponse is NetworkResult.Error && databaseResponse.isNullOrEmpty()) {
            textView.apply {
                text = apiResponse.msg.toString()
                isVisible = true
            }
        } else if (apiResponse is NetworkResult.Loading || apiResponse is NetworkResult.Success) {
            textView.isVisible = false
        }
    }
}