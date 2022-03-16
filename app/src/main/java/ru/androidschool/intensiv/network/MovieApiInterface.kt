package ru.androidschool.intensiv.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.MovieResponse

interface MovieApiInterface {

    /**
     * return список фильмов
     */
    @GET("movie/now_playing")
    fun getAllMovies(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru"
    ): Call<MovieResponse>

    /**
     *  return список новых фильмов
     */
    @GET("movie/upcoming")
    fun getAllNovelties(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru"
    ): Call<MovieResponse>

    /**
     *  return список популярных фильмов
     */
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru"
    ): Call<MovieResponse>

    /**
     * return список популярных сериалов
     */
    @GET("tv/popular")
    fun getPopularTV(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru"
    ): Call<MovieResponse>

    /**
     *  return информацию о фильме
     */
    @GET("movie")
    fun getMoviesDetails(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru",
        @Query("movie_id") movie_id: Int = 11
    ): Call<MovieResponse>

    /**
     * return информацию об актерском составе
     */
    @GET("movie/{movie_id}/credits")
    fun getMoviesCrewDetails(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru",
        @Query("movie_id") movie_id: Int = 11
    ): Call<MovieResponse>
}
