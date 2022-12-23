package com.exfarnanda1945.my_foody_course.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.exfarnanda1945.my_foody_course.model.ResultsItem
import com.exfarnanda1945.my_foody_course.util.Constants.FAVORITE_RECIPES_TABLE_NAME

@Entity(tableName = FAVORITE_RECIPES_TABLE_NAME)
data class FavoriteRecipesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val result: ResultsItem
)