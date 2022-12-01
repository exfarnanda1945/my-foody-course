package com.exfarnanda1945.my_foody_course.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class FoodRecipe(
	@field:SerializedName("results")
	val results: List<ResultsItem>
) : Parcelable