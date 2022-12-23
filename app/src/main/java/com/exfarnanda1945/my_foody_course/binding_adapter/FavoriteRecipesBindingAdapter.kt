package com.exfarnanda1945.my_foody_course.binding_adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.exfarnanda1945.my_foody_course.adapter.FavoriteRecipesAdapter
import com.exfarnanda1945.my_foody_course.data.database.entities.FavoriteRecipesEntity

object FavoriteRecipesBindingAdapter {

    @BindingAdapter("setViewVisibility", "setData", requireAll = true)
    @JvmStatic
    fun setVisibility(
        view: View,
        favoritesEntity: List<FavoriteRecipesEntity>?,
        mAdapter: FavoriteRecipesAdapter?
    ) {
        when (view) {
            is RecyclerView -> {
                val dataCheck = favoritesEntity.isNullOrEmpty()
                view.isVisible = !dataCheck
                if (!dataCheck) {
                    favoritesEntity.let {
                        mAdapter?.setData(it)
                    }
                }
            }
            else -> view.isVisible = favoritesEntity.isNullOrEmpty()
        }
    }

}