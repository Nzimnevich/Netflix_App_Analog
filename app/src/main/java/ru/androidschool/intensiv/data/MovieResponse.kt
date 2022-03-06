package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var movies: List<MyMovie>
)
