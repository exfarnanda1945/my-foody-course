package com.exfarnanda1945.my_foody_course.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ExtendedIngredientsItem(
	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("amount")
	val amount: Double,

	@field:SerializedName("unit")
	val unit: String,

	@field:SerializedName("original")
	val original: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("consistency")
	val consistency: String
) : Parcelable