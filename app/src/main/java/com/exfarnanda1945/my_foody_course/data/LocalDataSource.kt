package com.exfarnanda1945.my_foody_course.data

import com.exfarnanda1945.my_foody_course.data.database.RecipesDao
import com.exfarnanda1945.my_foody_course.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readDatabase(): Flow<List<RecipesEntity>> = recipesDao.readRecipes()

    suspend fun insertDatabase(recipesEntity: RecipesEntity) =
        recipesDao.insertRecipes(recipesEntity)
}