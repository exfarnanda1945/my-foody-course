package com.exfarnanda1945.my_foody_course.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResultsItem(
	@field:SerializedName("sustainable")
	val sustainable: Boolean,

	@field:SerializedName("glutenFree")
	val glutenFree: Boolean,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("aggregateLikes")
	val aggregateLikes: Int,

	@field:SerializedName("readyInMinutes")
	val readyInMinutes: Int,

	@field:SerializedName("sourceUrl")
	val sourceUrl: String,

	@field:SerializedName("dairyFree")
	val dairyFree: Boolean,

	@field:SerializedName("vegetarian")
	val vegetarian: Boolean,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("summary")
	val summary: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("veryHealthy")
	val veryHealthy: Boolean,

	@field:SerializedName("vegan")
	val vegan: Boolean,

	@field:SerializedName("cheap")
	val cheap: Boolean,

	@field:SerializedName("extendedIngredients")
	val extendedIngredients: List<ExtendedIngredientsItem>,

	@field:SerializedName("license")
	val license: String,

	@field:SerializedName("sourceName")
	val sourceName: String,
) : Parcelable