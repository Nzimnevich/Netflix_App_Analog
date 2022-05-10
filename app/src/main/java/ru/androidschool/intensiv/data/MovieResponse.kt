package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName
import ru.androidschool.intensiv.data.vo.MyMovie

data class MovieResponse(
    @SerializedName("page")
    var page: Int?,
    @SerializedName("results")
    var movies: List<MyMovie>?
)
