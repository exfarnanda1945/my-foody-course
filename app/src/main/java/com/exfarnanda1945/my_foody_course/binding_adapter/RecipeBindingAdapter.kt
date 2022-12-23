package com.exfarnanda1945.my_foody_course.binding_adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.exfarnanda1945.my_foody_course.data.database.entities.RecipesEntity
import com.exfarnanda1945.my_foody_course.model.FoodRecipe
import com.exfarnanda1945.my_foody_course.util.NetworkResult

object RecipeBindingAdapter {

    @BindingAdapter("readApiResponse","readDatabase", requireAll = true)
    @JvmStatic
    fun handleReadDataError(
        view: View,
        apiResponse: NetworkResult<FoodRecipe>?,
        database: List<RecipesEntity>?
    ) {
        when (view) {
            is ImageView -> view.isVisible =
                apiResponse is NetworkResult.Error && database.isNullOrEmpty()
            is TextView -> {
                view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                view.text = apiResponse?.msg.toString()
            }

        }
    }

}