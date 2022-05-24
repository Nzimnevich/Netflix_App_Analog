package ru.androidschool.intensiv.data.vo

import com.google.gson.annotations.SerializedName

data class Actor(
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("gender")
    var gender: Int,
    @SerializedName("known_for_department")
    var known_for_department: String,
    @SerializedName("name")
    var fullName: String?,
    @SerializedName("original_name")
    var originalName: String?,
    @SerializedName("popularity")
    var popularity: Float,
    @SerializedName("cast_id")
    var castId: Int,
    @SerializedName("character")
    var character: String,
    @SerializedName("credit_id")
    var creditId: String,
    @SerializedName("order")
    var order: Int,
) {
    @SerializedName("profile_path")
    var image: String? = null
        get() = "https://image.tmdb.org/t/p/w500$field"
}
