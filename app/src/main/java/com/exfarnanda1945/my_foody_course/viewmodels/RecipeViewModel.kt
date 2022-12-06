package com.exfarnanda1945.my_foody_course.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.exfarnanda1945.my_foody_course.util.Constants
import com.exfarnanda1945.my_foody_course.util.Constants.QUERY_ADD_RECIPE_INFO
import com.exfarnanda1945.my_foody_course.util.Constants.QUERY_API_KEY
import com.exfarnanda1945.my_foody_course.util.Constants.QUERY_DIET
import com.exfarnanda1945.my_foody_course.util.Constants.QUERY_FILL_INGREDIENTS
import com.exfarnanda1945.my_foody_course.util.Constants.QUERY_NUMBER
import com.exfarnanda1945.my_foody_course.util.Constants.QUERY_TYPE

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    // HashMap is a collection which contains pairs of object.
    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = "10"
        queries[QUERY_API_KEY] = Constants.API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFO] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
}