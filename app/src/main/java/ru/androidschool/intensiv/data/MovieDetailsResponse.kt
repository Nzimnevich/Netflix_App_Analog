package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName
import ru.androidschool.intensiv.BuildConfig

data class MovieDetailsResponse(
    @SerializedName("title")
    var title: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("genres")
    var genres: List<Genres>?,
    @SerializedName("overview")
    var description: String?,
    @SerializedName("vote_average")
    var rating: Float,
    @SerializedName("production_companies")
    var productionCompanies: List<ProductCompany>?,
    @SerializedName("release_date")
    var releaseDate: String?

) {
    @SerializedName("backdrop_path")
    var backdrop_image: String? = null
        get() = "${BuildConfig.POSTER_PATH}$field"
}

data class Genres(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String
) {
    override fun toString(): String {
        return name
    }
}

data class ProductCompany(
    @SerializedName("id")
    var page: Int?,
    @SerializedName("name")
    var name: String?

)
