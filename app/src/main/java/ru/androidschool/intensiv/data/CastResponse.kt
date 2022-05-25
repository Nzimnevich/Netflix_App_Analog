package ru.androidschool.intensiv.data

import com.google.gson.annotations.SerializedName
import ru.androidschool.intensiv.data.dto.Actor

class CastResponse(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("cast")
    var cast: List<Actor>?
)
