package com.exfarnanda1945.my_foody_course.util

object Constants {
    // API
    const val API_KEY = "eae6ab421f554d2a8f5ff823ec9467ea"
    const val BASE_URL = "https://api.spoonacular.com"
    const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"

    // Query Api
    const val QUERY_SEARCH = "query"
    const val QUERY_NUMBER = "number"
    const val QUERY_API_KEY = "apiKey"
    const val QUERY_TYPE = "type"
    const val QUERY_DIET = "diet"
    const val QUERY_ADD_RECIPE_INFO = "addRecipeInformation"
    const val QUERY_FILL_INGREDIENTS = "fillIngredients"

    // Room Database
    const val DATABASE_NAME = "recipes_database"
    const val RECIPES_TABLE_NAME = "recipes_table"
    const val FAVORITE_RECIPES_TABLE_NAME = "favorite_recipes_table"
    const val FOOD_JOKE_TABLE_NAME = "food_joke_table"

    // Bottom Sheet and Preferences
    const val DEFAULT_RECIPES_NUMBER = "50"
    const val DEFAULT_MEAL_TYPE = "main course"
    const val DEFAULT_DIET_TYPE = "gluten free"

    const val PREFERENCES_NAME = "foody_preferences"
    const val PREFERENCES_MEAL_TYPE = "mealType"
    const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
    const val PREFERENCES_DIET_TYPE = "dietType"
    const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
    const val PREFERENCES_BACK_ONLINE = "backOnline"

    const val RECIPE_BUNDLE_KEY = "recipeBundle"

}