package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName
import ru.androidschool.intensiv.BuildConfig

data class CastDetailsResponse(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("cast")
    var castGroup: List<actorDetail>,

)

data class actorDetail(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?
) {
    @SerializedName("profile_path")
    var profilePath: String? = null
        get() = "${BuildConfig.POSTER_PATH}$field"
}
