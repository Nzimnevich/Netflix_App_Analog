package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName
import ru.androidschool.intensiv.BuildConfig

data class MyMovie(
    @SerializedName(value = "original_title", alternate = ["name"])
    var title: String?,
    @SerializedName("overview")
    var description: String?,
    @SerializedName("release_date")
    var data: String?,
    @SerializedName("vote_average")
    var rating: Float?
) {
    @SerializedName("poster_path")
    var image: String? = null
        get() = "${BuildConfig.POSTER_PATH}$field"
}
