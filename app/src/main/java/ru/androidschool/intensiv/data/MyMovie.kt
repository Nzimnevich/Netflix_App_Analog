package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class MyMovie(
    @SerializedName("original_title")
    var title: String?,
    @SerializedName("overview")
    var description: String?,
    @SerializedName("poster_path")
    var image: String?,
    @SerializedName("release_date")
    var data: String?,
    @SerializedName("vote_average")
    var rating: Float?

)
