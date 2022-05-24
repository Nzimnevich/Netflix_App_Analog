package ru.androidschool.intensiv.data.vo

import com.google.gson.annotations.SerializedName
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.MovieDetailsResponse
import ru.androidschool.intensiv.data.database.MovieEntity

data class MyMovie(
    @SerializedName("id")
    var id: Int,
    @SerializedName(value = "original_title", alternate = ["name"])
    var title: String?,
    @SerializedName("overview")
    var description: String?,
    @SerializedName("release_date")
    var data: String?,
    @SerializedName("vote_average")
    var rating: Float
) {
    @SerializedName("poster_path")
    var image: String? = null
        get() = "${BuildConfig.POSTER_PATH}$field"

    @SerializedName("backdrop_path")
    var backdrop_image: String? = null
        get() = "${BuildConfig.POSTER_PATH}$field"

    companion object {
        fun convertToMovieEntity(dto: MyMovie): MovieEntity {
            var movieEntity = MovieEntity(
                title = dto.title ?: "",
                path = dto.backdrop_image ?: "",
                version = 1,
                id = dto.id
            )
            return movieEntity
        }

        fun convertMovieEntityToMovie(movieEntity: MovieEntity): MyMovie {
            var myMovie = MyMovie(
                title = movieEntity.title,
                id = movieEntity.id.toInt(),
                description = "",
                data = "",
                rating = 1f
            )
            myMovie.backdrop_image = movieEntity.path
            return myMovie
        }

        fun convertListMovieEntityToMovie(moviesEntity: List<MovieEntity>): List<MovieEntity> {
            moviesEntity.forEach { convertMovieEntityToMovie(it) }
            return moviesEntity
        }

        fun convertToMovieEntity(movieDetailsResponse: MovieDetailsResponse): MovieEntity? {
            var movieEntity =
                movieDetailsResponse.id?.let {
                    MovieEntity(
                        title = movieDetailsResponse.title ?: "",
                        path = movieDetailsResponse.backdrop_image ?: "",
                        version = 1,
                        id = it
                    )
                }
            return movieEntity
        }
    }
}
