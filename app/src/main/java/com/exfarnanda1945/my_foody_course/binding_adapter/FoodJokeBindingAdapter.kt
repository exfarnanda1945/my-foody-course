package com.exfarnanda1945.my_foody_course.binding_adapter

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.exfarnanda1945.my_foody_course.data.database.entities.FoodJokeEntity
import com.exfarnanda1945.my_foody_course.model.FoodJoke
import com.exfarnanda1945.my_foody_course.util.NetworkResult
import com.google.android.material.card.MaterialCardView

object FoodJokeBindingAdapter {

    @BindingAdapter("readApiResult", "readDatabaseResult", requireAll = true)
    @JvmStatic
    fun setCardAndProgressBarView(
        view: View,
        apiResponse: NetworkResult<FoodJoke>?,
        database: List<FoodJokeEntity>?
    ) {
        when (apiResponse) {
            is NetworkResult.Loading -> {
                when (view) {
                    is ProgressBar -> view.isVisible = true
                    is MaterialCardView -> view.isVisible = false
                }
            }
            is NetworkResult.Error -> {
                when (view) {
                    is ProgressBar -> view.isVisible = false
                    is MaterialCardView -> {
                        if (database != null) {
                            view.isVisible = database.isNotEmpty()
                        }
                    }
                }
            }
            is NetworkResult.Success -> {
                when (view) {
                    is ProgressBar -> view.isVisible = false
                    is MaterialCardView -> view.isVisible = true
                }
            }
            else -> {}
        }

    }

    @BindingAdapter("readApiResultForError", "readDatabaseResultForError", requireAll = true)
    @JvmStatic
    fun setErrorViewsVisibility(
        view: View,
        apiResponse: NetworkResult<FoodJoke>?,
        database: List<FoodJokeEntity>?
    ){
        if(database !=null){
            if(database.isEmpty()){
                view.isVisible = true
                when(view){
                    is TextView -> {
                        if(apiResponse !=null){
                            view.text = apiResponse.msg.toString()
                        }
                    }
                }
            }
        }
        if(apiResponse is NetworkResult.Success){
            view.isVisible = false
        }
    }
}