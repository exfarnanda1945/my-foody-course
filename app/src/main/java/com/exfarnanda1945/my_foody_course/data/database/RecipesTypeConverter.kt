package com.exfarnanda1945.my_foody_course.data.database

import androidx.room.TypeConverter
import com.exfarnanda1945.my_foody_course.model.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun foodRecipeDataToString(foodRecipe: FoodRecipe): String {
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipeData(dataStr: String): FoodRecipe {
        val listType = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(dataStr, listType)
    }
}