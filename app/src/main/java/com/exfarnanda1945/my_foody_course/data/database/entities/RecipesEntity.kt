package com.exfarnanda1945.my_foody_course.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.exfarnanda1945.my_foody_course.model.FoodRecipe
import com.exfarnanda1945.my_foody_course.util.Constants.RECIPES_TABLE_NAME


// Store json object into room
@Entity(tableName = RECIPES_TABLE_NAME)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    // when fetch a new data from api, the data in room will replaced all
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}