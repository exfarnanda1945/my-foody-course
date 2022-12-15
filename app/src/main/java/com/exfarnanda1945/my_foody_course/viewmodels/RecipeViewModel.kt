package com.exfarnanda1945.my_foody_course.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.exfarnanda1945.my_foody_course.data.DataStoreRepository
import com.exfarnanda1945.my_foody_course.util.Constants.API_KEY
import com.exfarnanda1945.my_foody_course.util.Constants.DEFAULT_DIET_TYPE
import com.exfarnanda1945.my_foody_course.util.Constants.DEFAULT_MEAL_TYPE
import com.exfarnanda1945.my_foody_course.util.Constants.DEFAULT_RECIPES_NUMBER
import com.exfarnanda1945.my_foody_course.util.Constants.QUERY_ADD_RECIPE_INFO
import com.exfarnanda1945.my_foody_course.util.Constants.QUERY_API_KEY
import com.exfarnanda1945.my_foody_course.util.Constants.QUERY_DIET
import com.exfarnanda1945.my_foody_course.util.Constants.QUERY_FILL_INGREDIENTS
import com.exfarnanda1945.my_foody_course.util.Constants.QUERY_NUMBER
import com.exfarnanda1945.my_foody_course.util.Constants.QUERY_SEARCH
import com.exfarnanda1945.my_foody_course.util.Constants.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false

    val readMealAndDietType = dataStoreRepository.read
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()


    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else {
            if (backOnline) {
                Toast.makeText(getApplication(), "Back Online", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }

        }
    }

    private fun saveBackOnline(backOnline: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }
    }

    fun saveMealAndDietType(
        mealTypeParam: String,
        mealTypeParamId: Int,
        dietTypeParam: String,
        dietTypeParamId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.save(mealTypeParam, mealTypeParamId, dietTypeParam, dietTypeParamId)
        }
    }

    // HashMap is a collection which contains pairs of object.
    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        applyQueryBase(queries)

        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType

        return queries
    }

    fun applySearchQueries(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        applyQueryBase(queries)

        queries[QUERY_SEARCH] = searchQuery

        return queries
    }

    private fun applyQueryBase(queries: HashMap<String, String>): HashMap<String, String> {
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFO] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
}