package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName

class CastResponse(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("cast")
    var cast: List<Actor>?
)
