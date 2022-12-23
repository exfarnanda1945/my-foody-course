package com.exfarnanda1945.my_foody_course.data

import com.exfarnanda1945.my_foody_course.data.network.FoodRecipesApi
import com.exfarnanda1945.my_foody_course.model.FoodJoke
import com.exfarnanda1945.my_foody_course.model.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> =
        foodRecipesApi.getRecipes(queries)

    suspend fun searchRecipes(queries: Map<String, String>): Response<FoodRecipe> =
        foodRecipesApi.searchRecipes(queries)

    suspend fun getFoodJoke(apiKey:String): Response<FoodJoke> =
        foodRecipesApi.getFoodJoke(apiKey)
}