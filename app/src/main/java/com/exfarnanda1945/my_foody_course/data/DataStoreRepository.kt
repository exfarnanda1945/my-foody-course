package com.exfarnanda1945.my_foody_course.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.exfarnanda1945.my_foody_course.util.Constants.DEFAULT_DIET_TYPE
import com.exfarnanda1945.my_foody_course.util.Constants.DEFAULT_MEAL_TYPE
import com.exfarnanda1945.my_foody_course.util.Constants.PREFERENCES_BACK_ONLINE
import com.exfarnanda1945.my_foody_course.util.Constants.PREFERENCES_DIET_TYPE
import com.exfarnanda1945.my_foody_course.util.Constants.PREFERENCES_DIET_TYPE_ID
import com.exfarnanda1945.my_foody_course.util.Constants.PREFERENCES_MEAL_TYPE
import com.exfarnanda1945.my_foody_course.util.Constants.PREFERENCES_MEAL_TYPE_ID
import com.exfarnanda1945.my_foody_course.util.Constants.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    // initialize preferences key
    private object PreferenceKey {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
    }

    suspend fun saveBackOnline(backOnline: Boolean) {
        context.dataStore.edit { preference ->
            preference[PreferenceKey.backOnline] = backOnline
        }
    }

    suspend fun save(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) {
        context.dataStore.edit { preference ->
            preference[PreferenceKey.selectedMealType] = mealType
            preference[PreferenceKey.selectedMealTypeId] = mealTypeId
            preference[PreferenceKey.selectedDietType] = dietType
            preference[PreferenceKey.selectedDietTypeId] = dietTypeId

        }
    }

    val read: Flow<MealAndDietType> = context.dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val selectedMealType = preferences[PreferenceKey.selectedMealType] ?: DEFAULT_MEAL_TYPE
        val selectedMealTypeId = preferences[PreferenceKey.selectedMealTypeId] ?: 0
        val selectedDietType = preferences[PreferenceKey.selectedDietType] ?: DEFAULT_DIET_TYPE
        val selectedDietTypeId = preferences[PreferenceKey.selectedDietTypeId] ?: 0

        MealAndDietType(selectedMealType, selectedMealTypeId, selectedDietType, selectedDietTypeId)
    }

    val readBackOnline: Flow<Boolean> = context.dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map {
        it[PreferenceKey.backOnline] ?: false
    }


}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int,
)