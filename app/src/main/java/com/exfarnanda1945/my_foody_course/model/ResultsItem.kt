package com.exfarnanda1945.my_foody_course.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ResultsItem(
    @field:SerializedName("sustainable")
    val sustainable: Boolean,

    @field:SerializedName("glutenFree")
    val glutenFree: Boolean,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("aggregateLikes")
    val aggregateLikes: Int? = 0,

    @field:SerializedName("readyInMinutes")
    val readyInMinutes: Int? = 0,

    @field:SerializedName("sourceUrl")
    val sourceUrl: String? = null,

    @field:SerializedName("dairyFree")
    val dairyFree: Boolean,

    @field:SerializedName("vegetarian")
    val vegetarian: Boolean,

    @field:SerializedName("id")
    val id: Int? = 0,

    @field:SerializedName("summary")
    val summary: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("veryHealthy")
    val veryHealthy: Boolean,

    @field:SerializedName("vegan")
    val vegan: Boolean,

    @field:SerializedName("cheap")
    val cheap: Boolean,

    @field:SerializedName("extendedIngredients")
    val extendedIngredients: @RawValue List<ExtendedIngredientsItem>,

    @field:SerializedName("license")
    val license: String? = null,

    @field:SerializedName("sourceName")
    val sourceName: String? = null,
) : Parcelable