package com.exfarnanda1945.my_foody_course.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExtendedIngredientsItem(
    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("amount")
    val amount: Double? = 0.0,

    @field:SerializedName("unit")
    val unit: String? = null,

    @field:SerializedName("original")
    val original: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("consistency")
    val consistency: String? = null
) : Parcelable