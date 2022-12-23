package com.exfarnanda1945.my_foody_course.data.database

import androidx.room.*
import com.exfarnanda1945.my_foody_course.data.database.entities.FavoriteRecipesEntity
import com.exfarnanda1945.my_foody_course.data.database.entities.FoodJokeEntity
import com.exfarnanda1945.my_foody_course.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {
    // replace data in database when fetch from api
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipes(favoriteRecipesEntity: FavoriteRecipesEntity)

    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun readFavoriteRecipes(): Flow<List<FavoriteRecipesEntity>>

    @Delete()
    suspend fun deleteFavoriteRecipes(favoriteRecipesEntity: FavoriteRecipesEntity)

    @Query("DELETE FROM favorite_recipes_table")
    suspend fun deleteAllFavoriteRecipes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

    @Query("SELECT * FROM food_joke_table ORDER BY id ASC")
     fun readFoodJoke():Flow<List<FoodJokeEntity>>
}