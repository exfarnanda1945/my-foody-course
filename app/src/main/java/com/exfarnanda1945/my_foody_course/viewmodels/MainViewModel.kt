package com.exfarnanda1945.my_foody_course.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.exfarnanda1945.my_foody_course.data.Repository
import com.exfarnanda1945.my_foody_course.data.database.entities.FavoriteRecipesEntity
import com.exfarnanda1945.my_foody_course.data.database.entities.FoodJokeEntity
import com.exfarnanda1945.my_foody_course.data.database.entities.RecipesEntity
import com.exfarnanda1945.my_foody_course.model.FoodJoke
import com.exfarnanda1945.my_foody_course.model.FoodRecipe
import com.exfarnanda1945.my_foody_course.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    // Room Database
    val readRecipe: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()
    val readFavoriteRecipes: LiveData<List<FavoriteRecipesEntity>> =
        repository.local.readFavoriteRecipes().asLiveData()
    val readFoodJoke: LiveData<List<FoodJokeEntity>> = repository.local.readFoodJoke().asLiveData()


    private fun insertRecipe(recipesEntity: RecipesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }
    }

    fun insertFavoriteRecipes(favoriteRecipesEntity: FavoriteRecipesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteRecipes(favoriteRecipesEntity)
        }
    }

    fun deleteFavoriteRecipe(favoriteRecipesEntity: FavoriteRecipesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteRecipe(favoriteRecipesEntity)
        }
    }

    fun deleteAllFavoriteRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavoriteRecipes()
        }
    }

    private fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFoodJoke(foodJokeEntity)
        }
    }


    // Retrofit
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    val searchRecipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    val foodJokeResponse: MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()


    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(queries: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(queries)
    }

    fun getFoodJoke(apiKey: String) = viewModelScope.launch {
        getFoodJokeSafeCall(apiKey)
    }

    private suspend fun getFoodJokeSafeCall(apiKey: String) {
        foodJokeResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getFoodJoke(apiKey)
                foodJokeResponse.value = handleFoodJokeResponse(response)

                val responseValue = foodJokeResponse.value!!.data
                if (responseValue != null) {
                    offlineHandleFoodJoke(responseValue)
                }
            } catch (e: Exception) {
                foodJokeResponse.value = NetworkResult.Error("Food Joke not found.")
            }
        } else {
            foodJokeResponse.value = NetworkResult.Error(msg = "No Internet Connection.")
        }
    }

    private suspend fun searchRecipesSafeCall(queries: Map<String, String>) {
        searchRecipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchRecipes(queries)
                searchRecipesResponse.value = handleFoodRecipesResponse(response)
            } catch (e: Exception) {
                searchRecipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            searchRecipesResponse.value = NetworkResult.Error(msg = "No Internet Connection.")
        }
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)

                val responseValue = recipesResponse.value!!.data
                if (responseValue != null) {
                    offlineHandleRecipes(responseValue)
                }
            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineHandleRecipes(responseValue: FoodRecipe) {
        val recipesEntity = RecipesEntity(responseValue)
        insertRecipe(recipesEntity)

    }

    private fun offlineHandleFoodJoke(foodJokeValue: FoodJoke) {
        val foodJokeEntity = FoodJokeEntity(foodJokeValue)
        insertFoodJoke(foodJokeEntity)

    }


    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        return when {
            response.message().toString()
                .contains("timeout") -> NetworkResult.Error("Error Timeout.")
            response.code() == 402 -> NetworkResult.Error("Api key is limited.")
            response.body()!!.results.isEmpty() -> NetworkResult.Error("Recipes not found.")
            response.isSuccessful -> NetworkResult.Success(response.body()!!)
            else -> NetworkResult.Error(response.message())
        }
    }

    private fun handleFoodJokeResponse(response: Response<FoodJoke>): NetworkResult<FoodJoke> {
        return when {
            response.message().toString()
                .contains("timeout") -> NetworkResult.Error("Error Timeout.")
            response.code() == 402 -> NetworkResult.Error("Api key is limited.")
            response.isSuccessful -> NetworkResult.Success(response.body()!!)
            else -> NetworkResult.Error(response.message())
        }
    }

    private fun hasInternetConnection(): Boolean {
        /*
        * Get connectivity service
        * Class that answers queries about the state of network connectivity.
        * It also notifies applications when network connectivity changes.
        * */
        val connectionManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // checking active network
        val activeNetwork = connectionManager.activeNetwork ?: return false
        //checking capabilities network
        val capabilities = connectionManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}