package com.exfarnanda1945.my_foody_course.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.exfarnanda1945.my_foody_course.model.FoodJoke
import com.exfarnanda1945.my_foody_course.util.Constants.FOOD_JOKE_TABLE_NAME

@Entity(tableName = FOOD_JOKE_TABLE_NAME)
class FoodJokeEntity(
    @Embedded
    var foodJoke:FoodJoke
) {
    @PrimaryKey(autoGenerate = false)
    var id:Int = 0
}