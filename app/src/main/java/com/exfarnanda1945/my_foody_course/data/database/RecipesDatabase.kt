package com.exfarnanda1945.my_foody_course.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.exfarnanda1945.my_foody_course.data.database.entities.FavoriteRecipesEntity
import com.exfarnanda1945.my_foody_course.data.database.entities.FoodJokeEntity
import com.exfarnanda1945.my_foody_course.data.database.entities.RecipesEntity

@Database(
    entities = [RecipesEntity::class, FavoriteRecipesEntity::class,FoodJokeEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}