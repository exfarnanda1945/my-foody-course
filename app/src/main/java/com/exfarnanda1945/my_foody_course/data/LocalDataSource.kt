package com.exfarnanda1945.my_foody_course.data

import com.exfarnanda1945.my_foody_course.data.database.RecipesDao
import com.exfarnanda1945.my_foody_course.data.database.entities.FavoriteRecipesEntity
import com.exfarnanda1945.my_foody_course.data.database.entities.FoodJokeEntity
import com.exfarnanda1945.my_foody_course.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readRecipes(): Flow<List<RecipesEntity>> = recipesDao.readRecipes()

    suspend fun insertRecipes(recipesEntity: RecipesEntity) =
        recipesDao.insertRecipes(recipesEntity)

    fun readFavoriteRecipes(): Flow<List<FavoriteRecipesEntity>> = recipesDao.readFavoriteRecipes()

    suspend fun insertFavoriteRecipes(favoriteRecipesEntity: FavoriteRecipesEntity) =
        recipesDao.insertFavoriteRecipes(favoriteRecipesEntity)

    suspend fun deleteFavoriteRecipe(favoriteRecipesEntity: FavoriteRecipesEntity) =
        recipesDao.deleteFavoriteRecipes(favoriteRecipesEntity)

    suspend fun deleteAllFavoriteRecipes() = recipesDao.deleteAllFavoriteRecipes()

     fun readFoodJoke(): Flow<List<FoodJokeEntity>> = recipesDao.readFoodJoke()

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) =
        recipesDao.insertFoodJoke(foodJokeEntity)

}